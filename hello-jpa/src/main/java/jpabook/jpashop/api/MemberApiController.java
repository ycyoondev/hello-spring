package jpabook.jpashop.api;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;

@RestController // controller + responsebody
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberApiController {

    public final MemberService memberService;

    /*
    V1은 Entity 자체를 파라미터로 사용한다.
    이는 요청 JSON 형태를 고정하는 것으로 유연하지 못하다.
    입력과 Entity 둘다 변경이 어려워지며, 특히 entity가 변경될때 문제가 발생할 수 있다.
    Entity는 다양한 개발자들이 사용하기때문에 개인적으로 사용할 수 없다.
    -> 절때 Entity로 요청을 받거나 주지 않는다!
     */
    @PostMapping("/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    /*
    V2은 DTO처럼 사용한다.
    이는 entity와 request를 중간에서 맵핑해준다.
    따라서 Validation을 체크하기 위해 DTO에 설정할 수 있으며
    DTO는 Entity보다 개인적으로 사용할 수 있다.
     */
    @PostMapping("/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {
        Member member = new Member();
        member.setName(request.getName());

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PutMapping("/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdateMemberRequest request) {
        memberService.update(id, request.getName()); // 쿼리
        Member findMember = memberService.findOne(id); // 로직 (분리가 좋음)
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }

    @GetMapping("/v2/members")
    public Result memberv2() {
        List<Member> findMembers = memberService.findMembers();
        List<MemberDTO> collect = findMembers.stream()
                .map(m -> new MemberDTO(m.getName()))
                .collect(Collectors.toList());
        return new Result(collect);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class MemberDTO {
        private String name;
    }

    @Data
    static class UpdateMemberRequest {
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse {
        private Long id;
        private String name;
    }

    @Data
    static class CreateMemberRequest {
        @NotEmpty
        private String name;
    }

    @Data
    static class CreateMemberResponse {
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }
}
