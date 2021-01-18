package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model){
        model.addAttribute("memberForm", new MemberForm());

        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result){
        // Member Entity를 직접 사용하면 안된다.
        // Entity에 벨리데이션을 넣기 시작하면 지저분해 질 뿐만 아니라
        // 화면에서 넘어오는 데이터와 Entit 데이터가 항상 일치하지 않기 때문에..

        if (result.hasErrors()){
            return "members/createMemberForm";
        }
        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());
        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);
        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        // 엔티티를 최대한 순수하게 유지를 해야 된다.
        // 오직 핵심 비지니스 로직만 가져야 하고 화면을 위한 로직을 가져선 안된다.
        // 유지보수가 힘들어진다.
        // 예제에서는 엔티티를 화면에 직접 뿌렸지만, 실무적으로 복잡해지면
        // dto를 가지고 화면에 꼭 필요한 데이터들만 가지고 화면에 뿌리는 것을 추천한다.
        // 여기서는 엔티티의 변화 없이 화면에서 뿌릴 수 있기 때문에 선택했다.
        // api를 만들 때는 절대 엔티티를 그대로 반환해서는 안된다.
        return "members/memberList";
    }
}
