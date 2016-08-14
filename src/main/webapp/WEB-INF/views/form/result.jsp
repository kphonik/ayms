<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>forms</title>

    <script type="text/javascript" src="<s:url value="/assets/form/result.js"/>"></script>
    <link href="<s:url value="/assets/form.css"/>" rel="stylesheet"/>

</head>
<body>

<div class="row">

    <h2>${result.definition.name} <small>${result.definition.description}</small></h2>
    <ol class="breadcrumb">
        <li><a href="<s:url value="/"/>">Home</a></li>
        <li class="active"><a href="<s:url value="/form/${result.definition.id}/result"/>">${result.definition.description}</a></li>
    </ol>

    <div id="result-dtl"></div>

        <form id="grid-stack-frm" method="post" class="form-horizontal">
            <div class="grid-stack"></div>
            <hr>
            <a href="<s:url value="/form/${result.definition.id}/result/${result.id}/delete"/>" type="button" role="button" class="btn btn-danger">Delete</a>
            <a href="<s:url value="/form/${result.definition.id}/result"/>" type="button" role="button" class="btn btn-default">Cancel</a>
            <button id="grid-stack-frm-submit" type="button" class="btn btn-primary">Save</button>
        </form>

    </div>

</div>

</body>
</html>