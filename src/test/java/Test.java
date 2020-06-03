import java.util.List;

/**
 * Copyright (c) 2020 XiaoMi Inc. All Rights Reserved.
 * Created by: jiangbowen <jiangbowen@xiaomi.com>.
 * On 2020/4/28
 */
public class Test {

    static class Person {
        private String name;
        private List<String> kids;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<String> getKids() {
            return kids;
        }

        public void setKids(List<String> kids) {
            this.kids = kids;
        }
    }

    public static void main(String[] args) {
        Person person = new Person();
        person.setName("233");
        System.out.println(person);

        int i = 1;
        double j = 2.0;

        if (i < j) {
            System.out.println(23333);
        }
    }

}
