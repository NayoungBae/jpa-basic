package hello.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        //createEntityManagerFactory 매개변수 : persistence-unit의 name 기재
        // persistence.xml에 적어놓은 persistence-unit 정보를 불러옴
        // DB와 연결할 수 있는 상태로 만들어줌
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("hello");
        //createEntityManager 꺼내주기
        EntityManager em = emf.createEntityManager();

        //실제 동작하는 코드 작성
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            //JPA는 DB 테이블 대상으로 코드를 짜지 않음.
            //객체를 대상으로 코드를 짬
            //페이징도 가능!
            List<Member> selectResult = em.createQuery("select m from Member as m", Member.class)
                    .setFirstResult(0)  //0번부터
                    .setMaxResults(2)   //2개 데이터
                    .getResultList();   //조회

            for(Member member : selectResult) {
                System.out.println("member.name = " + member.getName());
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            //실행 코드가 끝나면 닫아줘야함
            em.close();
        }

        //어플리케이션이 끝나면 닫아줘야함
        emf.close();

    }
}
