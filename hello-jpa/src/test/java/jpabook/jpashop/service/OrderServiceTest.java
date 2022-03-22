package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {
        //given
        Member member = createMember();

        Book book = createBook();

        int orderCount = 2;

        //when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        Assert.assertEquals("상품주문시 상태는 order", OrderStatus.ORDER, getOrder.getStatus());
        Assert.assertEquals("주문한 상품 종류 수가 정확해야한다.", 1, getOrder.getOrderItems().size());
        Assert.assertEquals("주문가격은 가격 * 수량이다", 20000, getOrder.getTotalPrice());
        Assert.assertEquals("주문수량만큼 재고가 줄어야한다", 8, book.getStockQuantity());
    }

    @Test(expected = NotEnoughStockException.class)
    public void 재고수량초과() throws Exception {
        //given
        Member member = createMember();

        Book book = createBook();

        int orderCount = 20;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        fail("실패: 재고수량 예외가 발생해야함");
    }

    @Test
    public void 주문취소() throws Exception {
        //given
        Member member = createMember();
        Book book = createBook();

        Long orderId = orderService.order(member.getId(), book.getId(), 2);
        //when
        orderService.cancelOrder(orderId);

        //then
        Order order = orderRepository.findOne(orderId);

        Assert.assertEquals("주문 상태 확인", OrderStatus.CANCEL, order.getStatus());
        Assert.assertEquals("재고가 잘 복구되었는지 확인", 10, book.getStockQuantity());

    }
    private Book createBook() {
        Book book = new Book();
        book.setName("책1");
        book.setPrice(10000);
        book.setStockQuantity(10);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "거리", "123"));
        em.persist(member);
        return member;
    }




}