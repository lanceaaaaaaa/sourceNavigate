package com.luban.note.controller;

import com.luban.common.ResultModel;
import com.luban.note.model.NoteModel;
import com.luban.note.service.NoteService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("note")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @RequestMapping("toMain")
    public ModelAndView toMain(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/note/main.html");
        return mv;
    }

    @RequestMapping(value = "toSelectOption")
    public ModelAndView toSelectOption(NoteModel noteModel, Model model){
        ModelAndView mv = new ModelAndView();
        NoteModel dataModel = noteService.getNoteList(noteModel).get(0);
        model.addAttribute("noteModel",dataModel);
        mv.setViewName("/note/selectoption.html");
        return  mv;
    }

    @RequestMapping(value = "toEdit")
    public ModelAndView toEdit(String option,NoteModel noteModel, Model model){
        ModelAndView mv = new ModelAndView();
        //获取一个节点数据
        List<NoteModel> noteList = noteService.getNoteList(noteModel);
        model.addAttribute("option",option);
        if(noteList != null && noteList.size() == 1){
            model.addAttribute("noteModel",noteList.get(0));
        }
        mv.setViewName("/note/edit.html");
        return  mv;
    }

    @RequestMapping(value = "toQuery")
    public ModelAndView toQuery(String option,NoteModel noteModel, Model model){
        ModelAndView mv = new ModelAndView();
        model.addAttribute("noteModel",noteModel);
        mv.setViewName("/note/query.html");
        return  mv;
    }

    @RequestMapping("getNoteList")
    @ResponseBody
    public List<NoteModel> getNoteList(NoteModel noteModel){
        return noteService.getNoteList(noteModel);
    }

    @RequestMapping("saveOrUpdateNote")
    @ResponseBody
    public ResultModel saveOrUpdateNote(NoteModel noteModel){
        try {
                if(StringUtils.isNoneBlank(noteModel.getNoteId())){
                    noteService.updateNote(noteModel);
                }else{
                    noteService.addNote(noteModel);
                }
                return ResultModel.success("新增或者更新节点成功",noteModel);
            }catch(Exception ex){
                return ResultModel.error("新增或者更新节点失败");
        }
    }

    @RequestMapping("delNote")
    @ResponseBody
    public ResultModel delNote(NoteModel noteModel){
        try {
            noteService.delNote(noteModel);
            return ResultModel.success("删除节点成功",noteModel);
        }catch(Exception ex){
            return ResultModel.error("删除节点失败");
        }
    }

    @RequestMapping("saveAllNotes")
    @ResponseBody
    public ResultModel saveAllNotes(@RequestBody List<NoteModel> nodeList){
        try {
            noteService.saveAllNotes(nodeList);
            return ResultModel.success("成功");
        }catch(Exception ex){
            ex.printStackTrace();
            return ResultModel.error("失败");
        }
    }

    @RequestMapping("toChild")
    public ModelAndView toChild(NoteModel noteModel, Model model){
        ModelAndView mv = new ModelAndView();
        model.addAttribute("noteModel",noteModel);
        mv.setViewName("/note/child.html");
        return mv;
    }
}
