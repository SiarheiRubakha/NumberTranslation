<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>

<form name="translation" method="post" action="/submit_form">
    <label for="number">Введите число</label>
    <input id="number"
           type="number"
           name="number"
           size="40"
           max= "${maxValue}">

    <#if result??>
        <p>Результат:</p>
        <p>${result}</p>
    </#if>

    <#if error??>
        <p>Ошибка:</p>
        <p>${error}</p>
    </#if>


    <p>
        <input type="submit" value="Отправить">
        <input type="reset" value="Очистить">
    </p>
</form>

</body>
</html>