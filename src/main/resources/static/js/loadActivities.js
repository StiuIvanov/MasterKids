$('#loadActivities').click(() => {
    reloadActivities();
});


$('#selectActivity').change(() => {
    sortActivities();
});

function reloadActivities() {
    $('#activities-container').empty();
    fetch("http://localhost:8080/admin/get-all")
        .then(response => response.json())
        .then(json => json.forEach(child => {
            let $tr = $("<tr></tr>");

            let numberOfActivities = child.activityDTO.length;
            let $tdName = $("<td></td>")
                .text(child.name);

            if (numberOfActivities > 0) {
                $tdName.attr("rowspan", numberOfActivities);
            }

            $tr.append($tdName);
            let tdActivity0;
            if (numberOfActivities > 0) {
                let activityText = child.activityDTO[0].activity;
                tdActivity0 = $("<td></td>").text(activityText);
            } else {
                tdActivity0 = $("<td></td>").text("-");
            }

            $tr.append(tdActivity0);

            if (numberOfActivities > 1) {
                $('#activities-container').append($tr);
                for (let i = 1; i < numberOfActivities; i++) {
                    let $tr1 = $("<tr></tr>");

                    let tempTd = $("<td></td>").text(child.activityDTO[i].activity);
                    $tr1.append(tempTd);

                    $('#activities-container').append($tr1);
                    console.log("IN");
                }

            } else {
                $('#activities-container').append($tr);
            }
        }));
}

function sortActivities() {
    $('#activities-container').empty();
    let value = $('#selectActivity').val();

    fetch("http://localhost:8080/admin/sort-activity/" + value)
        .then(response => response.json())
        .then(data => {
            data.students.forEach(s => {
                let $tr = $("<tr></tr>");
                let $tdChildName = $("<td></td>").text(s.name);
                let $activityName = $("<td></td>").text(value);
                $tr.append($tdChildName).append($activityName);

                $('#activities-container').append($tr);
            });
        });
}


