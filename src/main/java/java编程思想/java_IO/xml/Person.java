package java编程思想.java_IO.xml;//: xml/Person.java
// Use the XOM library to write and read XML
// {Requires: nu.xom.Node; You must install
// the XOM library from http://www.xom.nu }

import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Serializer;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

/**
 * 对象 序列 化 的 一个 重要 限制 是它 只是 Java 的 解决 方案： 只有 Java 程序 才能 反 序列 化 这种 对象。
 * 一种 更具 互 操作 性的 解决 方案 是将 数据 转换 为 XML 格式， 这可 以 使其 被 各种各样 的 平台 和 语言 使用。
 * 因为 XML 十分 流行， 所以 用 它来 编程 时 的 各种 选择 不胜枚举， 包括 随 JDK 发布 的 javax. xml.* 类 库。
 * 我 选择 使用 Elliotte Rusty Harold 的 开源 XOM 类 库（ 可从 www. xom. nu 下载 并 获得 文档），
 * 因为 它看起来 最简单， 同时 也是 最 直观 的 用 Java 产生 和 修改 XML 的 方式。 另外， XOM 还 强调 了 XML 的 正确性。
 * 作为 一个 示例， 假设 有一个 Person 对象， 它 包含 姓 和 名， 你想 将它 们 序列 化 到 XML 中。
 * 下面 的 Person 类有 一个 getXML() 方法， 它 使用 XOM 来 产生 被 转换 为 XML 的 Element 对象 的 Person 数据；
 * 还有 一个 构造 器， 接受 Element 并从 中 抽取 恰当 的 Person 数据（ 注意， XML 示例 都在 它们 自己的 子目录 中）：
 */
public class Person {
    private String first, last;

    public Person(String first, String last) {
        this.first = first;
        this.last = last;
    }

    // Produce an XML Element from this Person object:
    public Element getXML() {
        Element person = new Element("person");
        Element firstName = new Element("first");
        firstName.appendChild(first);
        Element lastName = new Element("last");
        lastName.appendChild(last);
        person.appendChild(firstName);
        person.appendChild(lastName);
        return person;
    }

    // Constructor to restore a Person from an XML Element:
    public Person(Element person) {
        first = person.getFirstChildElement("first").getValue();
        last = person.getFirstChildElement("last").getValue();
    }

    public String toString() {
        return first + " " + last;
    }

    // Make it human-readable:
    public static void
    format(OutputStream os, Document doc) throws Exception {
        Serializer serializer = new Serializer(os, "ISO-8859-1");
        serializer.setIndent(4);
        serializer.setMaxLength(60);
        serializer.write(doc);
        serializer.flush();
    }

    public static void main(String[] args) throws Exception {
        List<Person> people = Arrays.asList(
                new Person("Dr. Bunsen", "Honeydew"),
                new Person("Gonzo", "The Great"),
                new Person("Phillip J.", "Fry"));
        System.out.println(people);
        Element root = new Element("people");
        for (Person p : people)
            root.appendChild(p.getXML());
        Document doc = new Document(root);
        format(System.out, doc);
        format(new BufferedOutputStream(new FileOutputStream(
                "People.xml")), doc);
    }
} /* Output:
[Dr. Bunsen Honeydew, Gonzo The Great, Phillip J. Fry]
<?xml version="1.0" encoding="ISO-8859-1"?>
<people>
    <person>
        <first>Dr. Bunsen</first>
        <last>Honeydew</last>
    </person>
    <person>
        <first>Gonzo</first>
        <last>The Great</last>
    </person>
    <person>
        <first>Phillip J.</first>
        <last>Fry</last>
    </person>
</people>
*///:~
