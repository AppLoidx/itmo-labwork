# ITMO Laboratory work

Использованные библиотеки:

|Библиотека||
|-----|------|
|**gson**|Для сериализации объектов JSON|
|**org.everit.json.schema**|Для валидации вводимых JSON-данных|

Зависимости в pom.xml:
```xml
    <dependencies>
        <!--FOR JSON VALIDATION-->
        <dependency>
            <groupId>org.everit.json</groupId>
            <artifactId>org.everit.json.schema</artifactId>
            <version>1.3.0</version>
        </dependency>

        <!--JSON PARSER-->
        <dependency>
            <groupId>com.google.code.gson</groupId>
            <artifactId>gson</artifactId>
            <version>2.8.5</version>
        </dependency>
    </dependencies>
```

Задание 5 по лабораторной работе:
```
Разработанная программа должна удовлетворять следующим требованиям:
Класс, коллекцией экземпляров которого управляет программа, должен реализовывать сортировку по умолчанию.
Для хранения необходимо использовать коллекцию типа java.util.ArrayDequeue.
При запуске приложения коллекция должна автоматически заполняться значениями из файла.
Имя файла должно передаваться программе с помощью переменной окружения.
Данные должны храниться в файле в формате csv.
При остановке приложения текущее состояние коллекции должно автоматически сохраняться в файл.
Чтение данных из файла необходимо реализовать с помощью класса java.io.BufferedReader.
Запись данных в файл необходимо реализовать с помощью класса java.io.BufferedWriter.
Все реализованные команды (см. ниже) должны быть задокументированы в формате javadoc.
Формат задания объектов в командах - json.
```
В интерактивном режиме программа должна поддерживать выполнение следующих команд:

* `remove_last`: удалить последний элемент из коллекции
* `remove {element}`: удалить элемент из коллекции по его значению
* `info`: вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)
* `add {element}`: добавить новый элемент в коллекцию
* `add_if_max {element}`: добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции
* `show`: вывести в стандартный поток вывода все элементы коллекции в строковом представлении
* `add_if_min {element}`: добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции

## Сущности коллекций
Для добавления объектов в коллекцию были выбраны несколько типов, наследованные от общего класса Person.

Класс Person - это абстрактный класс дял всех сущностей коллекций.

В нем переопределены **equals** и **hashcode**:
```java
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return height == person.height &&
                name.equals(person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(height, name);
    }
```

Как видно из листинга, класс имеет две значащие поля:
* name - имя объекта
* height - высота объекта

Сортируются элементы в коллекции по их росту:
```java
public int compareTo(Person o) {
        return o.height - height;
    }
```

## Конкретные сущности
Перейдем к конкретным реализациям класса Person. Все классы пока ничем не отличаются, кроме как приветственного сообщения, но это понадобится нам в будушем
### Human
![](https://github.com/AppLoidx/itmo-labwork/blob/master/src/main/resources/assets/menhera.png)

Класс человека
```java
public String helloMessage() {
        return "Hello!";
    }
```
<hr>

### Neko
![](https://github.com/AppLoidx/itmo-labwork/blob/master/src/main/resources/assets/hanekawa.png)

Полу-кошка
```java
public String helloMessage() {
        return "Nyan nyan nyan ni hao nyan!";
    }
```
<hr>

### Tanuki
![](https://github.com/AppLoidx/itmo-labwork/blob/master/src/main/resources/assets/raphtalia.png)

Полу-енот (на самом деле, оборотни)
```java
@Override
    public String helloMessage() {
        return "Hey, hello!";
    }
```

## Начало работы

Так как данные хранятся в csv (Comma-Separated Values), в которой изначально нету какой-либо структуры, кроме запятых, то нужно её
сделать.

Было бы очень не удобно записывать данные в таком виде:
```csv
Human, 170, img/res/assets/img1.png, img/res/assets/img2.png, Cyle
```
Такой вид уже более приемлем:
```csv
class=Neko, name=China, height=140, imgSource=assets/china.png, playable=nyan.mp4
```

Первый способ позволяет легко парсить информацию с файлов, что позволяет не усложнять десериализацию объектов из этого файла. Но он хорош, если с этим файлом всегда будет работать компьютер, то есть изначально оговоренная структура.

Но даже, если это так, то это очень сильно бьет по адаптивности программы. Иначе говоря, мы не сможем изменить структуру нашего проекта без, возможно, больших усилий, которые мы потратим на перепись нескольких модулей проекта

Второй способ позволяет не задавать жесткую структуру и он понятен человеку. Разве что, нам нужно будет написать к нему парсер.

Парсер называется CSVExpressionReader (я не долго думал над именем) и имеет метод `readString` для получения значения определенного поля:

```java
class CSVExpressionReader {
    static String readString(String field, String data){

        // ленивый квантор .+?
        String regex = ",*?[ ]*" + field + "=.*?(,+|$)";

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(data);
        if (m.find()) return m.group().split("=")[1].replace(",", "");

        return null;

    }
}
```

Здесь мы используем регулярное выражение и ленивые кванторы

### Что такое ленивые кванторы?

Дело в том, что метасимволы `*`, `.` и `+` **жадны**, т.е. обнаруживают максимально, а не минимально возможное совпадение. В итоге обнаружение совпадения почти всегда начинается не с начала, а с конца текста и продолжается в обратном направлении до тех пор, пока совпадение не будет найдено.

В качестве примера рассмотрим строку:
```html
This offer is not available to customers living in <b>AK</b> and <b>HI</b>. 
```
Применив регулярное выражение:
```re
<[Bb]>.*</[Bb]>
```
Мы получим: `<b>AK</b> and <b>HI</b>`

Хотя можно было бы и ожидать два разных выражения в тегах.

И что если жадное совпадение не требуется? В таком случае, можно воспользоваться ленивыми кванторами. Она называются ленивыми потому, что обозначают совпадение с минимальным, а не максимальным количеством символов.

Ленивые кванторы обозначаются с присоединяемым к ним знаком `?`. У каждого жадного квантора имеется свой ленивый эквивалент:

|Жадный квантор|Ленивый квантор|
|--------------|---------------|
|*|\*?|
|+|+?|
|{n,}|{n,}?|

Так, мы можем изменить предыдущее регулярное выражение на:
```re
<[Bb]>.*?</[Bb]>
```
И получить два совпадения: `<b>AK</b>` и `<b>HI</b>`


## Парсинг персонажей из csv файла
У нас есть csv-файл с данными и персонажах:
```csv
class=Neko, name=China, height=140, imgSource=assets/china.png, playable=nyan.mp4
class=Neko, name=Hanekawa, height=180, imgSource=assets/hanekawa.png
class=Human, name=Lilly, height=150, imgSource=assets/trombosit.png
class=Tanuki, name=Raphtalia, height=170, imgSource=assets/raphtalia.png
```

Для этой цели используется класс Parser и его метод readPerson(String line), который считывает персонажа из строки:

```java
    private static Person readPerson(String line){

        int height = 0;

        String classOf      = CSVExpressionReader.readString("class", line);
        String name         = CSVExpressionReader.readString("name", line);
        String imgSource    = CSVExpressionReader.readString("imgSource", line);
        String temp         = CSVExpressionReader.readString("height", line);
        String playable     = CSVExpressionReader.readString("playable", line);

        if (temp!=null && temp.matches("[0-9]+")){
            height = Integer.parseInt(temp);
        }
        if (classOf==null || name==null) return null;

        Person p;
        if ((p=PersonFabric.getPerson(classOf, name, height))==null) return null;

        p.setSpritePath(imgSource); // image path
        p.setPlayRes(playable);     // video resource path

        return p;
    }
```

Если отсутствиют какие-либо поля или, если они не валидны, то метод возвращает null, говоря, что невозможно считать персонажа.

Теперь, когда мы можем прочитать одну строку - прочитаем весь csv файл:
```java
    public static List<Person> readPersons(File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        List<Person> persons = new ArrayList<>();
        while((line=br.readLine())!=null){
            Person person = readPerson(line);
            if (person!=null) persons.add(person);
        }

        return persons;

    }
```

## Углубимся в создании персонажей (Паттерн Абстрактная фабрика)
Если вы заметили, то при считвании персонажа использовался метод `PersonFabric.getPerson(classOf, name, height)`.

Его предназначение в том, чтобы создавать объекты классов, но возвращать некий асбтрактный (единый) класс.

Иными словами, внутри этого метода создаться один из конкретных классов, например, Human или Neko, но сам метод возвратит Person, так как для коллекции без разницы какого типа будет объект, лишь бы подтипом Person.

Для этого, ему передается строковое имя класса : **classOf**<br>
Имя и рост: **name**, **height**

Сам метод короткий:
```java
public class PersonFabric {

    public static Person getPerson(String classOf, String name, int height){

        for (PersonClass p: PersonClass.values()){

            if (p.toString().equals(classOf.toUpperCase())){
                return p.getInstance(name, height);
            }
        }
        return null;
    }
}
```

Во многом благодаря enum'у PersonClass, у которого есть метод getInstance:
```java
public enum PersonClass {

    HUMAN{
        @Override
        public Person getInstance(String name, int height) {
            return new Human(name, height);
        }
    },

    NEKO {
        @Override
        public Person getInstance(String name, int height) {
            return new Neko(name, height);
        }
    },

    TANUKI {
        @Override
        public Person getInstance(String name, int height) {
            return new Tanuki(name, height);
        }
    };

    public abstract Person getInstance(String name, int height);
}

```

Сейчас не видно, но в будущем это послужит хорошей идеей из-за гибкости, которую она нам дает. Например, если допусить, что будут классы с дополнительными полями (сейчас у всех только два поля, которые есть в конструкторах), то можно будет не передавать отдельные аргументы в метод getPerson, а какой-нибудь **map** и проверять на наличие элементов.

Далее, мы сможем оперделить создание класса в enum и дальше не беспокоится об его экземпляре.

## PersonArray - коллекция объектов Person

По заданию необходимо было сделать ArrayDeque, поэтому я создал класс PersonArray, который наследуется от ArrayDeque<Person>:
```java
public class PersonArray extends ArrayDeque<Person> {
    private String changedDate;
    {
        changedDate = SimpleDateFormat.getDateTimeInstance().format(new Date());
    }

    public String getChangedDate() {
        return changedDate;
    }

    public void setChangedDate(){
        this.changedDate = SimpleDateFormat.getDateTimeInstance().format(new Date());
    }

    public void sort(){
        PersonArray deque = new PersonArray();
        List<Person> temp = this.stream().sorted().collect(Collectors.toList());
        this.clear();
        this.addAll(temp);
    }

    public void saveTo(File file) throws IOException {
        // тут было много непонятного
    }

    private void writeToFile(File file) throws IOException {
        // здесь много кода...
    }

    @Override
    public int hashCode() {
        int c = 0;
        for (Person p : this){
            c = c*31 + p.hashCode();
        }
        return c;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof PersonArray){
            List<Person> aObj = new ArrayList<>(((PersonArray) obj));
            List<Person> thisObj = new ArrayList<>(this);
            Collections.sort(aObj);
            Collections.sort(thisObj);

            if (aObj.size() == thisObj.size()){
                for (int i = 0; i < aObj.size() - 1; i++){
                    if (!aObj.get(i).equals(thisObj.get(i))){
                        return false;
                    }
                }
                return true;
            }
            return false;
        }
        return false;
    }
}

```

Сначала, рассмотрим поле changedDate и метод setChangedDate.

С помощью этих методов можно устанавливать время последнего изменения коллекции. При необходимости, можно сделать и второе поле, указывающее на дату инициализации.

Если происходит какое-либо изменении в объекте, то вызывается метод setChangedDate().

### hashcode & equals

Далее, с соблюдением контракта equals происходит переопределение двух методов. Для хэш-кода - высчитваются хэш-коды всех его элементов, а для equals оба PersonArray сортируются и каждый его объект сравнивается с соответствующим.

### Почему нельзя было написать так?
```java
@Override
public boolean equals(Object obj) {
    return obj.hashCode() == this.hashCode();
}
```
Это был бы рабочий код, но важно понимать, что есть такое понятие как коллизия, а именно то, что у разных объектов может быть одинаковый хэш-код. Тогда, наш метод equals будет работать совершенно неправильно.

В таком случае, даже какой-нибудь класс Dog может быть эквивалентен нашему PersonArray, что просто немыслимо.

Можно просто обратиться к контракту equals:
* Во время выполнения приложения при многократном вызове для одного и того же объекта метод hashCode должен всегда возвращать одно и то же целое число при условии, что никакая информация, используемая при сравнении этого объекта с другими методом equals, не изменилась. Однако не требуется, чтобы это же значение оставалось тем же при другом выполнении приложения.
* Если два объекта равны согласно результату работы equals (Object), то при вызове для каждого из них метода hashCode должны получиться одинаковые целочисленные значения.
* Если метод equals (Object) утверждает, что два объекта не равны один другому, это не означает, что метод hashCode возвратит для них разные числа. Однако программист должен понимать, что генерация разных чисел для неравных объектов может повысить производительность хеш-таблиц.

Статья про эту тему: https://vk.com/@apploidxxx-equals-hash-code-to-string

### Команды и множество лямбда выражений :)

Каждая команда является отдельным объектом, управляемый специальным контейнером в виде класса CommandContainer, который управляет множеством этих команд.

#### Command

Все команды является субклассами Command, у которого есть два основных метода:
```java
    public String getName(){
        return name;
    }

    public CollectionAction getAction(String context){
        return action;
    }
```
Листинг CollectionAction:
```java
@FunctionalInterface
public interface CollectionAction {
    void action(PersonArray col);
}
```

С помощью метода getName - происходит выборка команды по имени дешифратором.

Интересен же метод getAction, который возвращает функциональный интерфейс для взаимодействия с коллекцтей переданной в качестве аргумента.

Посмотрим его реализацию в конкретных командах:

#### Add-If-Max
```java
    @Override
    public CollectionAction getAction(String context) {
        String json = JSONContextReader.readJSONContext(context);

        try {
            JSONPersonParser.validate(new JSONObject(json));
            Person person = JSONPersonParser.getPerson(json);

            return col -> {
                if (person == null) {
                    System.err.println("Персонаж с таким классом не существует. Проверьте валидность ваших данных.");
                } else {
                    Optional<Person> maxPerson = col.stream().max(Comparator.comparing(Person::getHeight));
                    if (!maxPerson.isPresent()) CollectionEditor.addPerson(col, person);
                    else if (maxPerson.get().getHeight() < person.getHeight()) {
                        CollectionEditor.addPerson(col, person);
                    }
                }
            };
        } // exceptions catch here ...
    }
```

Если опустить детали, то выжным здесь является блок кода:
```java
Optional<Person> maxPerson = col.stream().max(Comparator.comparing(Person::getHeight));

if (!maxPerson.isPresent()) 
    CollectionEditor.addPerson(col, person);
else if (maxPerson.get().getHeight() < person.getHeight())
    CollectionEditor.addPerson(col, person);
```
И само лямбда выражение, охватывающее этот блок:
```java
 return col -> { /*  какие-то действия...*/ };
```
где col - это коллекция наших объектов, то есть PersonArray

#### Show

```java
@Override
public CollectionAction getAction(String context) {
    return col -> col.forEach(System.out::println);
}
```

