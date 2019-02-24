<<<<<<< HEAD
# grep
Выбор библиотеки: <br>

<ul>
<li>Apache Commons CLI: и строка с командой, и каждый параметр описываюются как объекты, а объект-парсер вызывает у них нужные для парсинга функции. Не очень удобно, нужно создавать много объектов, и писать много кода для обработки.</li>  
<li>google-options: для класса, описывающего команду, нужно согда дополнительный класс, описывающий параметры. Также нет возможности отделить параметры, не являющиеся ключами, пришлось бы сделать это вручную, поэтому часть смысла в использовании библиотеки теряется.</li> 
<li>Argparse4j: опять много кода, как в Apache Commons CLI. Также не получилось описывать ключи без дополнительного параметра (-i, -w)</li>  
<li><b>picocli</b>: описание аргумента - поле класса с аннотацией. Вызываем одну функцию, все поля инициализируются соответствующими аргументами. Показалось удобнее, чем делать то же самое, но с соданием объектов или классов.</li> 
</ul>
=======
# CLI
<img src="https://github.com/MariEliseeva/SoftwareDesign/blob/hw1-cli/Architecture.jpg">

Lexer - берёт строчку, разбивает на токены.<br>
Parser - разбивает токены по принадлежности к командам и объединяет в объекты Word токены, относящиеся к одному слову (имя команды, какой-то аргумент и т.д.). <br> 
Parser возвращает List\<Executable\>: объекты, которые хранят имя и аргументы команд и вычисляют их, когда требуется(делают подстановки переменных, замену кавычек и т.д.).<br>
Interpreter получает имя и аргументы команды с помощью Executable, создает команду по имени, используя CommandCreator, и запускает ее (все команды наследуются от интерфейса с методом run(Environment)).<br>
Environment хранит все значения переменных, которые нужны при подстановке, позволяет командам их менять, содержит промежуточные значения.
>>>>>>> hw1-cli
