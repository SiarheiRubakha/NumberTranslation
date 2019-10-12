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
            <#--Set name to input to let Spring resolve it by name in controller-->
           name="number"
           size="40">

    <#if result??>
        <p>Результат:</p>
        <p>${result}</p>
    </#if>

    <input type="submit" value="Отправить">
    <input type="reset" value="Очистить">
</form>

</body>
</html>