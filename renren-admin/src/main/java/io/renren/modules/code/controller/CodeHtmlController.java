package io.renren.modules.code.controller;

import io.renren.modules.code.entity.StatuEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CodeHtmlController {

    @RequestMapping("/apply/{id}")
    public String codeApply(@PathVariable String id, ModelMap map){
        map.put("id",id);
        return "modules/sys/dg_code_apply";
    }
}
