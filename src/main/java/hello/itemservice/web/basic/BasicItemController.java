package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor    // final 멤버변수만 사용하여 자동으로 생성자를 만들어줌.
public class BasicItemController {

    private final ItemRepository itemRepository;

    /*
        아이템 목록 화면을 렌더링
     */
    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    /*
        PathVariable로 넘어온 itemId로 상품 ID를 조회하고 모델에 담은 뒤 뷰 템플릿 호출
     */
    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

    /**
     * 상품 등록 POST 전달
     * content-type: application/x-www-form-urlencoded
     * 파라미터 형식의 쿼리 전달 : itemName=itemA&price=10000&quantity=10
     */
    /*@PostMapping("/add")
    public String addItemV1(@RequestParam String itemName,
                            @RequestParam int price,
                            @RequestParam Integer quantity,
                            Model model) {

        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);

        model.addAttribute("item", item);
        return "basic/item";
    }*/

    /**
     * @ModelAttribute의 기능
     * @ModelAttribute("item") Item item
     * 1. model.addAttribute("item", item); 자동 추가
     * 2. @ModelAttribute는 객체를 생성하고 요청 파라미터 값을 프로퍼티 접근법으로 자동으로 입력해준다.
     */
    /*@PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item, Model model) {
        itemRepository.save(item);
        //model.addAttribute("item", item); //자동 추가, 생략 가능
        return "basic/item";
    }*/

    /**
     * @ModelAttribute name 생략 가능
     * model.addAttribute(item); 자동 추가, 생략 가능
     * 생략시 model에 저장되는 name은 클래스명 첫글자만 소문자로 등록 Item -> item
     */
    /*@PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item) {
        itemRepository.save(item);
        return "basic/item";
    }*/

    /**
     * @ModelAttribute 자체 생략 가능
     * model.addAttribute(item) 자동 추가
     */
    /*@PostMapping("/add")
    public String addItemV4(Item item) {
        itemRepository.save(item);
        return "basic/item";
    }*/

    /**
     * PRG - Post/Redirect/Get
     * 리다이렉트를 함으로써 새로고침으로 인한 중복 등록 방지
     */
    /*@PostMapping("/add")
    public String addItemV5(Item item) {
        itemRepository.save(item);
        return "redirect:/basic/items/" + item.getId();
    }*/

    /*
        RedirectAttrivutes를 이용하여 PRG 방식 적용
     */
    @PostMapping("/add")
    public String addItemV6(Item item, RedirectAttributes redirectAttributes) {

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);    // status 파라미터에 따른 저장완료 화면 출력 목적

        return "redirect:/basic/items/{itemId}";
    }


    /*
        상품 수정 폼 렌더링 컨트롤러
     */
    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);

        return "basic/editForm";
    }

    /*
        상품 수정 POST 처리 컨트롤러
     */
    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";        // 상품 상세 화면으로 리다이렉트
                                                        // 스프링에서는 redirect:/ 로 편리한 리다이렉트 지원
                                                        // 컨트롤러에 매핑된 값을 그대로 사용할수 있다.
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("testA", 10000, 10));
        itemRepository.save(new Item("testB", 20000, 20));
    }
}
