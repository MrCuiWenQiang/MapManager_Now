$(function () {
   vm.init();
});

function apply() {
    return vm.apply()
}

var vm = new Vue({
    el: '#rrappt',
    data: {
        showList: true,
        title: null,
        code: {}
    },
    methods: {
        apply: function () {
            var id = $('#id').val();
            var status = $('#statusselect  option:selected').val();
            var expt = $('#exptimer  option:selected').val();
            var params = [id,status,expt];
            return params
        },
        init:function () {
           $.ajax({
                type:"POST",
                url:"../sys/code/getStatus",
                contentType: "application/json",
                success:function (r) {
                    if(r.code === 0){
                        var stus= r.status;
                        var ts= r.timers;
                        for (var i=0;i<stus.length;i++) {
                            var value = stus[i].value;
                            var lable = stus[i].name;
                            $('#statusselect').append("<option value="+value+">"+lable+"</option>");
                        }
                        for (var i=0;i<ts.length;i++) {
                            var value = ts[i].value;
                            var lable = ts[i].name;
                            $('#exptimer').append("<option value="+value+">"+lable+"</option>");
                        }
                    }
                }
            });
        }

    }
});