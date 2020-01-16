package juc.Stream_programming;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Arrays;
import java.util.List;

/**
 * 链式编程,流式计算
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Book {
    private int id;
    private String bookName;
    private double price;

    public static void main(String[] args) {
        // 链式编程
//        Book book1 = new Book();
//        book1.setPrice(123).setId(2).setBookName("c++");

        // 流式计算
        Book book1 = new Book(11, "aa", 18);
        Book book2 = new Book(22, "bb", 19);
        Book book3 = new Book(33, "cc", 20);
        Book book4 = new Book(44, "dd", 21);
        Book book5 = new Book(55, "ee", 22);

        List<Book> list = Arrays.asList(book1, book2, book3, book4, book5);

        list.stream()
                .filter(x -> x.getId() % 2 == 0)
                .map(x -> x.getBookName().toUpperCase())
                .sorted((o1, o2) -> o2.compareTo(o1))
                .limit(1)
                .forEach(System.out::println);
    }
}
