$(function () {
        $("#jqGrid").jqGrid({
            url: baseURL + 'sys/code/list',
            datatype: "json",
            colModel: [
                {label: 'id', name: 'id', index: 'id', width: 50, key: true, hidden: true},
                {label: '单位名称', name: 'coltd', index: 'coltd', width: 80},
                {label: '联系人', name: 'linman', index: 'linman', width: 80},
                {label: '联系电话', name: 'tel', index: 'tel', width: 80},
                {label: '联系邮箱', name: 'email', index: 'email', width: 80},
                {label: '注册码', name: 'code', index: 'code', width: 150},
                {
                    label: '状态', name: 'status', index: 'status', width: 80, formatter: function (cellvalue) {
                        var text
                        switch (cellvalue) {
                            case 0: text = '正常'; break;
                            case 1: text = '停用'; break;
                            case 2: text = '审核中'; break;
                            case 3: text = '审核不通过'; break;
                            default: text  ="不明";
                        }
                        return text;
                    }
                },
                {label: '备注', name: 'remarks', index: 'remarks', width: 80},
                {label: '创建时间', name: 'createtimer', index: 'createtimer', width: 80},
                {label: '有效期至', name: 'expTimer', index: 'exp_timer', width: 80},
                {
                    label: '操作', width: 80, formatter: function (cellvalue, options, rowObject) {
                        return "<a class=\"btn btn-primary\" onclick=\"apply(" + options.rowId + ")\"><i class=\"fa fa-trash-o\"></i>&nbsp;审核</a>";
                    }
                }
            ],
            viewrecords: true,
            height: 385,
            rowNum: 10,
            rowList: [10, 30, 50],
            rownumbers: false,
            rownumWidth: 25,
            autowidth: true,
            multiselect: false,
            pager: "#jqGridPager",
            jsonReader: {
                root: "page.list",
                page: "page.currPage",
                total: "page.totalPage",
                records: "page.totalCount"
            },
            prmNames: {
                page: "page",
                rows: "limit",
                order: "order"
            },
            gridComplete: function () {
                //隐藏grid底部滚动条
                $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
            }
        });
    }
);

function apply(id) {
    var iflayero;
    layer.open({
        type: 2,
        // content:$('#apply').html(),
        content: baseURL + "apply/" + id,
        area: '600px',
        title: '审核',
        btn: ['确定', '取消'],
        success: function (layero, index) {
            iflayero = layero;
        },
        yes: function (index) {
            var iframeWin = window[iflayero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
            var params = iframeWin.apply();
            // {id,status,ext}
            //请求数据
            $.ajax({
                type: 'post',
                url: baseURL + 'sys/code/apply',
                contentType: "application/json",
                data: '{"id":' + params[0] + ',"status":"' + params[1] + '","ext":"' + params[2] + '"}',
                success: function (r) {
                    layer.msg(r.msg)
                    layer.close(index);
                    vm.reload()
                },
                fail: function (m) {
                    layer.msg('连接失败');
                    layer.close(index);
                }

            })

        },
        clean: function () {
        }
    });
}

var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        title: null,
        code: {}
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.code = {};
        },
        update: function (event) {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.getInfo(id)
        },
        saveOrUpdate: function (event) {
            $('#btnSaveOrUpdate').button('loading').delay(1000).queue(function () {
                var url = vm.code.id == null ? "sys/code/save" : "sys/code/update";
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.code),
                    success: function (r) {
                        if (r.code === 0) {
                            layer.msg("操作成功", {icon: 1});
                            vm.reload();
                            $('#btnSaveOrUpdate').button('reset');
                            $('#btnSaveOrUpdate').dequeue();
                        } else {
                            layer.alert(r.msg);
                            $('#btnSaveOrUpdate').button('reset');
                            $('#btnSaveOrUpdate').dequeue();
                        }
                    }
                });
            });
        },
        del: function (event) {
            var ids = getSelectedRows();
            if (ids == null) {
                return;
            }
            var lock = false;
            layer.confirm('确定要删除选中的记录？', {
                btn: ['确定', '取消'] //按钮
            }, function () {
                if (!lock) {
                    lock = true;
                    $.ajax({
                        type: "POST",
                        url: baseURL + "sys/code/delete",
                        contentType: "application/json",
                        data: JSON.stringify(ids),
                        success: function (r) {
                            if (r.code == 0) {
                                layer.msg("操作成功", {icon: 1});
                                $("#jqGrid").trigger("reloadGrid");
                            } else {
                                layer.alert(r.msg);
                            }
                        }
                    });
                }
            }, function () {
            });
        },
        getInfo: function (id) {
            $.get(baseURL + "sys/code/info/" + id, function (r) {
                vm.code = r.code;
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                page: page
            }).trigger("reloadGrid");
        }
    }
});