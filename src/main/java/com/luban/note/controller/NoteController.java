package com.luban.note.controller;

import com.luban.common.ResultModel;
import com.luban.note.model.NoteModel;
import com.luban.note.service.NoteService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@Controller
@RequestMapping("note")
public class NoteController {

    private static Logger log= LoggerFactory.getLogger(NoteController.class);

    @Autowired
    private NoteService noteService;

    @RequestMapping("toMain")
    public ModelAndView toMain(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("note/main.html");
        return mv;
    }

    @RequestMapping(value = "toSelectOption")
    public ModelAndView toSelectOption(NoteModel noteModel, Model model){
        ModelAndView mv = new ModelAndView();
        NoteModel dataModel = noteService.getNoteList(noteModel).get(0);
        model.addAttribute("noteModel",dataModel);
        mv.setViewName("note/selectoption.html");
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
        mv.setViewName("note/edit.html");
        return  mv;
    }

    @RequestMapping(value = "toQuery")
    public ModelAndView toQuery(String option,NoteModel noteModel, Model model){
        ModelAndView mv = new ModelAndView();
        model.addAttribute("noteModel",noteModel);
        mv.setViewName("note/query.html");
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
        mv.setViewName("note/child.html");
        return mv;
    }


    /**
     * 上传至服务器
     */
    @RequestMapping("/upload")
    @ResponseBody
    public ResultModel toServerDir(HttpServletRequest req, Model model, String nodeId){
        //从请求中获得文件对象
        MultipartHttpServletRequest r = (MultipartHttpServletRequest) req;

        // 获得具体文件,参数名与前端name属性一致
        MultipartFile file = r.getFile("file");
        // 确定服务器位置
        ServletContext servletContext = req.getServletContext();
        // 获得服务器/images文件路径,将来图片就存储在服务器的/images
        File parent = new File("/images");
        if (!parent.exists()){
            parent.mkdir();
        }
        System.out.println("服务器路径: parent = " + parent);
        // 获得文件名
        String originalFilename = file.getOriginalFilename();
        // 获得文件后缀
        String substring = originalFilename.substring(originalFilename.lastIndexOf("."));
        long millis = System.currentTimeMillis();


        // 组装文件名
        String fileName = millis+substring;
        System.out.println("最终文件名: fileName = " + fileName);
        File file1 = new File(parent,fileName);

        // 执行上传
        try {
            file.transferTo(file1);
        } catch (IOException e) {
            System.out.println("上传");
            e.printStackTrace();
            return ResultModel.error("失败");
        }
        System.out.println("上传成功");
        model.addAttribute("filepath","/images/"+fileName);

        NoteModel noteModel = new NoteModel();
        noteModel.setNoteId(nodeId);
        noteModel.setPicPath(parent + File.separator + fileName);
        noteService.updatePicPath(noteModel);

        return ResultModel.success("成功");
    }

    @RequestMapping("/downloadPic")
    public void downloadPic(HttpServletResponse response, String noteId){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/x-msdownload");
        try {
            NoteModel noteModel = new NoteModel();
            noteModel.setNoteId(noteId);
            List<NoteModel> noteList = noteService.getNoteList(noteModel);
            String picPath = noteList.get(0).getPicPath();
            if(StringUtils.isNoneBlank(picPath)){
                log.info("aaaaaaaaaaaaaa"+picPath);
                File file = new File( picPath);
                if (!file.exists()) {
                    throw new RuntimeException("File not found!");
                }
                long fileLength = file.length();
                response.setHeader("Content-Length", String.valueOf(fileLength));
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
                BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
                byte[] buff = new byte[2048];
                int bytesRead;
                while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                    bos.write(buff, 0, bytesRead);
                }
                bis.close();
                bos.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
