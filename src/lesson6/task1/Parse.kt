@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson6.task1

import lesson2.task2.daysInMonth
import java.lang.IllegalStateException
import kotlin.IllegalArgumentException
import kotlin.math.max

// Урок 6: разбор строк, исключения
// Максимальное количество баллов = 13
// Рекомендуемое количество баллов = 11
// Вместе с предыдущими уроками (пять лучших, 2-6) = 40/54

/**
 * Пример
 *
 * Время представлено строкой вида "11:34:45", содержащей часы, минуты и секунды, разделённые двоеточием.
 * Разобрать эту строку и рассчитать количество секунд, прошедшее с начала дня.
 */
fun timeStrToSeconds(str: String): Int {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result
}

/**
 * Пример
 *
 * Дано число n от 0 до 99.
 * Вернуть его же в виде двухсимвольной строки, от "00" до "99"
 */
fun twoDigitStr(n: Int) = if (n in 0..9) "0$n" else "$n"

/**
 * Пример
 *
 * Дано seconds -- время в секундах, прошедшее с начала дня.
 * Вернуть текущее время в виде строки в формате "ЧЧ:ММ:СС".
 */
fun timeSecondsToStr(seconds: Int): String {
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val second = seconds % 60
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

/**
 * Пример: консольный ввод
 */
fun main() {
    println("Введите время в формате ЧЧ:ММ:СС")
    val line = readLine()
    if (line != null) {
        val seconds = timeStrToSeconds(line)
        if (seconds == -1) {
            println("Введённая строка $line не соответствует формату ЧЧ:ММ:СС")
        } else {
            println("Прошло секунд с начала суток: $seconds")
        }
    } else {
        println("Достигнут <конец файла> в процессе чтения строки. Программа прервана")
    }
}


/**
 * Средняя (4 балла)
 *
 * Дата представлена строкой вида "15 июля 2016".
 * Перевести её в цифровой формат "15.07.2016".
 * День и месяц всегда представлять двумя цифрами, например: 03.04.2011.
 * При неверном формате входной строки вернуть пустую строку.
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30.02.2009) считается неверными
 * входными данными.
 */
fun dateStrToDigit(str: String): String {
    val mounths = mapOf(
        "января" to 1,
        "февраля" to 2,
        "марта" to 3,
        "апреля" to 4,
        "мая" to 5,
        "июня" to 6,
        "июля" to 7,
        "августа" to 8,
        "сентября" to 9,
        "октября" to 10,
        "ноября" to 11,
        "декабря" to 12
    )
    val parts = str.split(" ")
    if (parts.size != 3) return ""
    val day = parts[0].toInt()
    val mounth = mounths[parts[1]]
    val year = parts[2].toInt()
    if (mounth == null || day > daysInMonth(mounth, year)) return ""
    return String.format("%02d.%02d.%d", day, mounth, year)
}

/**
 * Средняя (4 балла)
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30 февраля 2009) считается неверными
 * входными данными.
 */
fun dateDigitToStr(digital: String): String {
    val namesOfMounths = listOf(
        "января",
        "февраля",
        "марта",
        "апреля",
        "мая",
        "июня",
        "июля",
        "августа",
        "сентября",
        "октября",
        "ноября",
        "декабря"
    )
    val parts = digital.split(".")
    if (parts.size != 3) return ""
    try {
        val day = parts[0].toInt()
        val mounth = parts[1].toInt()
        val year = parts[2].toInt()
        if (mounth !in 1..12 || day > daysInMonth(mounth, year)) return ""
        return String.format("%d %s %d", day, namesOfMounths[mounth - 1], year)
    } catch (e: NumberFormatException) {
        return ""
    }
}

/**
 * Средняя (4 балла)
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -89 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку.
 *
 * PS: Дополнительные примеры работы функции можно посмотреть в соответствующих тестах.
 */
fun flattenPhoneNumber(phone: String): String {
    var changedPhone = ""
    val allowedChars = listOf('(', ')', '+', '-', ' ')
    for (i in phone.indices) {
        val chr = phone[i]
        if (!chr.isDigit() && chr !in allowedChars || chr == '(' && !phone[i + 1].isDigit()) {
            return ""
        }
        if (chr.isDigit() || chr == '+') {
            changedPhone += chr
        }
    }
    return changedPhone
}

/**
 * Средняя (5 баллов)
 *
 * Результаты спортсмена на соревнованиях в прыжках в длину представлены строкой вида
 * "706 - % 717 % 703".
 * В строке могут присутствовать числа, черточки - и знаки процента %, разделённые пробелами;
 * число соответствует удачному прыжку, - пропущенной попытке, % заступу.
 * Прочитать строку и вернуть максимальное присутствующее в ней число (717 в примере).
 * При нарушении формата входной строки или при отсутствии в ней чисел, вернуть -1.
 */
fun bestLongJump(jumps: String): Int {
    val allowedSymbols = listOf("-", "%")
    val parts = jumps.split(" ")
    var isHaveDigit = false
    var result = -1
    for (part in parts) {
        var number = part.toIntOrNull()
        if (number == null) {
            if (part !in allowedSymbols) {
                return -1
            }
        } else {
            if (number > result) {
                result = number
            }
        }
    }
    return result
}

/**
 * Сложная (6 баллов)
 *
 * Результаты спортсмена на соревнованиях в прыжках в высоту представлены строкой вида
 * "220 + 224 %+ 228 %- 230 + 232 %%- 234 %".
 * Здесь + соответствует удачной попытке, % неудачной, - пропущенной.
 * Высота и соответствующие ей попытки разделяются пробелом.
 * Прочитать строку и вернуть максимальную взятую высоту (230 в примере).
 * При нарушении формата входной строки, а также в случае отсутствия удачных попыток,
 * вернуть -1.
 */
fun bestHighJump(jumps: String): Int {
    val allowed = setOf('+', '%', '-')
    val parts = jumps.split(" ")
    var result = -1
    for (i in parts.indices) {
        var number = parts[i].toIntOrNull()
        if (number == null) {
            if (!allowed.containsAll(parts[i].toSet())) {
                return -1
            }
        } else {
            if (
                number > result
                && parts.getOrElse(i + 1) { "" }.contains('+')
            ) {
                result = number
            }
        }
    }
    return result
}

/**
 * Сложная (6 баллов)
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * Про нарушении формата входной строки бросить исключение IllegalArgumentException
 */
fun plusMinus(expression: String): Int {
    val parts = expression.split(" ")
    var operator = "+"
    var operand = 0
    var result = 0
    for (i in 0..parts.size - 2) {
        val part = parts[i]
        if (
            i == 0 && (part == "+" || part == "-")
            || part.toIntOrNull() != null && parts[i + 1].toIntOrNull() != null
            || part.toIntOrNull() == null && parts[i + 1].toIntOrNull() == null
        ) {
            throw IllegalArgumentException()
        }
    }
    for (i in parts.indices) {
        val part = parts[i]
        if (part.length > 1 && (part[0] == '+' || part[0] == '-')) {
            throw IllegalArgumentException()
        }
        if (part == "+" || part == "-") {
            operator = part
        } else {
            operand = part.toInt()
        }
        if (i % 2 == 0) {
            result = if (operator == "+") result + operand else result - operand
        }
    }
    return result
}

/**
 * Сложная (6 баллов)
 *
 * Строка состоит из набора слов, отделённых друг от друга одним пробелом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Слова, отличающиеся только регистром, считать совпадающими.
 * Вернуть индекс начала первого повторяющегося слова, или -1, если повторов нет.
 * Пример: "Он пошёл в в школу" => результат 9 (индекс первого 'в')
 */
fun firstDuplicateIndex(str: String): Int {
    val parts = str.split(" ")
    var length = 0
    for (i in 0 until parts.size - 1) {
        if (parts[i].equals(parts[i + 1], ignoreCase = true)) {
            return str.indexOf(parts[i], length, ignoreCase = true)
        }
        length += parts[i].length
    }
    return -1
}

/**
 * Сложная (6 баллов)
 *
 * Строка содержит названия товаров и цены на них в формате вида
 * "Хлеб 39.9; Молоко 62; Курица 184.0; Конфеты 89.9".
 * То есть, название товара отделено от цены пробелом,
 * а цена отделена от названия следующего товара точкой с запятой и пробелом.
 * Вернуть название самого дорогого товара в списке (в примере это Курица),
 * или пустую строку при нарушении формата строки.
 * Все цены должны быть больше нуля либо равны нулю.
 */
fun mostExpensive(description: String): String {
    var maxPair = ("" to 0.0)
    if (description == "") return ""
    for (pair in description.split("; ")) {
        var sep = pair.split(" ")
        if (sep[1].toDouble() > maxPair.second) {
            maxPair = (sep[0] to sep[1].toDouble())
        }
    }
    return maxPair.first
}

/**
 * Сложная (6 баллов)
 *
 * Перевести число roman, заданное в римской системе счисления,
 * в десятичную систему и вернуть как результат.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: XXIII = 23, XLIV = 44, C = 100
 *
 * Вернуть -1, если roman не является корректным римским числом
 */
fun fromRoman(roman: String): Int {
    val romanToArabic = mapOf(
        "CM" to 900,
        "M" to 1000,
        "CD" to 400,
        "D" to 500,
        "XC" to 90,
        "C" to 100,
        "XL" to 40,
        "L" to 50,
        "IX" to 9,
        "X" to 10,
        "IV" to 4,
        "V" to 5,
        "I" to 1
    )
    var str = roman
    var res = 0
    for (i in str.indices) {
        if (roman[i].toString() !in romanToArabic.keys) return -1
        for ((key, value) in romanToArabic) {
            while (str.contains(key)) {
                str = str.removeRange(str.indexOf(key)..str.indexOf(key) + key.length - 1)
                res += value
            }
        }
    }
    return res
}

/**
 * Очень сложная (7 баллов)
 *
 * Имеется специальное устройство, представляющее собой
 * конвейер из cells ячеек (нумеруются от 0 до cells - 1 слева направо) и датчик, двигающийся над этим конвейером.
 * Строка commands содержит последовательность команд, выполняемых данным устройством, например +>+>+>+>+
 * Каждая команда кодируется одним специальным символом:
 *	> - сдвиг датчика вправо на 1 ячейку;
 *  < - сдвиг датчика влево на 1 ячейку;
 *	+ - увеличение значения в ячейке под датчиком на 1 ед.;
 *	- - уменьшение значения в ячейке под датчиком на 1 ед.;
 *	[ - если значение под датчиком равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей следующей командой ']' (с учётом вложенности);
 *	] - если значение под датчиком не равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей предыдущей командой '[' (с учётом вложенности);
 *      (комбинация [] имитирует цикл)
 *  пробел - пустая команда
 *
 * Изначально все ячейки заполнены значением 0 и датчик стоит на ячейке с номером N/2 (округлять вниз)
 *
 * После выполнения limit команд или всех команд из commands следует прекратить выполнение последовательности команд.
 * Учитываются все команды, в том числе несостоявшиеся переходы ("[" при значении под датчиком не равном 0 и "]" при
 * значении под датчиком равном 0) и пробелы.
 *
 * Вернуть список размера cells, содержащий элементы ячеек устройства после завершения выполнения последовательности.
 * Например, для 10 ячеек и командной строки +>+>+>+>+ результат должен быть 0,0,0,0,0,1,1,1,1,1
 *
 * Все прочие символы следует считать ошибочными и формировать исключение IllegalArgumentException.
 * То же исключение формируется, если у символов [ ] не оказывается пары.
 * Выход за границу конвейера также следует считать ошибкой и формировать исключение IllegalStateException.
 * Считать, что ошибочные символы и непарные скобки являются более приоритетной ошибкой чем выход за границу ленты,
 * то есть если в программе присутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *
 */
fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> {
    val result = mutableListOf<Int>()
    var sensor = cells / 2
    var commandCounter = 0
    var commandIndex = 0
    for (i in 0 until cells) {
        result.add(0)
    }

    while (commandCounter < limit && commandIndex < commands.length) {
        if (sensor >= cells) {
            throw IllegalStateException()
        }

        var command = commands[commandIndex]

        if (command == '>') {
            sensor += 1
        } else if (command == '<') {
            sensor -= 1
        } else if (command == '+') {
            result[sensor] += 1
        } else if (command == '-') {
            result[sensor] -= 1
        } else if (command == '[' && result[sensor] == 0) {
            var counter = indexAllOf(commands.substring(0, commandIndex), "]").size
            commandIndex = indexAllOf(commands, "]")[counter]
        } else if (command == ']' && result[sensor] != 0) {
            var counter = indexAllOf(commands.substring(0, commandIndex), "[").size
            commandIndex = indexAllOf(commands, "[")[counter - 1]
        }

        commandCounter += 1
        commandIndex += 1
    }
    return result
}

fun bracketsCorrect(str: String): Boolean {
    var counter = 0
    for (x in str) {
        if (x == '[') {
            counter += 1
        } else if (x == ']') {
            counter -= 1
        }
        if (counter < 0) {
            return false
        }
    }
    return true
}

fun indexAllOf(str: String, search: String): List<Int> {
    var index = str.indexOf(search)
    val result = mutableListOf<Int>()
    while (index != -1) {
        result.add(index)
        index = str.indexOf(search, index + 1)
    }
    return result
}