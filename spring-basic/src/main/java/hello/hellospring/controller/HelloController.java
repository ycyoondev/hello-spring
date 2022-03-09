package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    // 정적 페이지 내려줌
    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data", "hellow!");
        return "hello";
    }

    // GET에서 파람스 사용
    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam("name-pa") String name, Model model) {
        model.addAttribute("name2", name);
        return "hello-template";
    }

    // 문자를 내려줌
    @GetMapping("hello-string")
    @ResponseBody
    public String helloString(@RequestParam("name") String name) {
        return "hello " + name; // hello spring과 같이 됨
    }

    // API 방식으로 JSON 내려줌 (객체)
    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }

    static class Hello {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

}
