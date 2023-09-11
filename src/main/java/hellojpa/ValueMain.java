package hellojpa;

public class ValueMain {
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public ValueMain() {
    }

    public static void main(String[] args) {
        /** 기본 값 타입
         * */
        //TODO - int, double같은 기본타입(primitive type)은 항상 값을 복사함. 절대 공유X
        int a = 10;
        int b = a; //이때 a는 복사된다. a=10인 순간.

        a = 20;
        System.out.println("a = " + a); //a = 20
        System.out.println("b = " + b); //b = 10



        //TODO - Integer(래퍼클래스), String(특수클래스)는 공유 가능한 객체이지만 변경X
        Integer a1 = new Integer(10);
        Integer b1 = a1; //Ref만 넘어간다.

        //a를 20으로 바꾸면, 둘다 20으로 바뀐다. Ref가 넘어가기(공유) 때문에. ** 하지만 바꾸는 코드는 없다!!
        System.out.println("a1 = " + a1); //a1 = 10
        System.out.println("b1 = " + b1); //b1 = 10

        System.out.println("a==b : " + (a ==b));
        System.out.println("a1==b1 : " + (a1 ==b1));
        //얘는 내부에 값이 들어있기 때문에 true가 나온다.

        Address address1 = new Address("city", "street", "10000");
        Address address2 = new Address("city", "street", "10000");

        System.out.println("address1 == address2 : " + (address1 == address2));
        //java는 reference 값을 비교 하기 때문에 fals가 당연히 나온다.


        //TODO 동일성(identity) 비교 : 인스턴스의 참조값을 비교, == 사용
        //TODO 동등성(equivalence) 비교 : 인스턴스의 값을 비교, equals()사용, 값 타입일 경우 사용, 메소드를 적절히 재정의 해서 사용

        System.out.println("address1 equals address2 : " + (address1.equals(address2)));
        //false 가 나옴. override 안했기 때문에
        //Address class 내에 equals 를 만들면, true가 나온다.


        String A = "A";
        String B = "B";

        System.out.println("A == B : " + (A == B) );
        System.out.println("A.equals B : " + (A.equals(B)) );



    }
}
