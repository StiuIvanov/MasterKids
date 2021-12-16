const csrfHeaderName = document.head.querySelector('[name = "_csrf_header"]').content;
const csrfHeaderValue = document.head.querySelector('[name = "_csrf"]').content;


loadParent();

function loadParent() {
    $('#info-container').empty();

    fetch("http://localhost:8080/admin/get-parents")
        .then(response => response.json())
        .then(json => json.forEach(p => {

            console.log(p);

            let $tr = $("<tr></tr>");

            // First Cell
            let $tdParent = $("<td></td>");
            let $a = $("<a />", {
                text: p.firstName + ' ' + p.lastName + '  ',
            });
            $tdParent.append($a);

            // Second Cell
            let $td2 = $("<td></td>");
            let $formChildren = $("<form />", {
                method: "get",
                action: "/admin/children-info/" + p.username
            });
            let $buttonChildren = $("<button />", {
                text: "Children",
                class: "btn btn-info  mb-3"
            })
            $formChildren.append($buttonChildren);
            $td2.append($formChildren);

            //Append Elements to row
            $tr
                .append($tdParent)
                .append($td2);

            $('#info-container').append($tr);

        }));
}




