// reloadParents();
//
// function reloadParents() {
//     $('#activity-container').empty();
//     let id = window.location.pathname.split('/')[2];
//     fetch("http://localhost:8080/child/" + id + "/info")
//         .then(response => response.json())
//         .then(json => json.forEach(dto => {
//             // let htmlTableRowElement = document.createElement('tr');
//             //
//             // let htmlTableDataCellElementActivity = document.createElement('td');
//             // htmlTableDataCellElementActivity.textContent = dto.activity;
//             //
//             // let htmlTableDataCellElementAction = document.createElement('td');
//             //
//             // let htmlButtonElement2 = document.createElement('button');
//             // htmlButtonElement2.textContent = 'Delete';
//             // htmlButtonElement2.setAttribute('class', 'delete-btn');
//             // htmlButtonElement2.setAttribute('data-child-id', id);
//             // htmlButtonElement2.setAttribute('data-activity-name', dto.activity);
//             // htmlTableDataCellElementAction.appendChild(htmlButtonElement2);
//             //
//             //
//             // htmlTableRowElement.appendChild(htmlTableDataCellElementActivity);
//             // htmlTableRowElement.appendChild(htmlTableDataCellElementAction);
//             //
//             // $('#activity-container').append(htmlTableRowElement);
//             let tableRow = '<tr>' +
//                 '<td>' + dto.activity + '</td>' +
//                 '<td>' + '<form action="/activity/remove" method="post">' +
//                     '<input type="hidden" name="method" value="delete"/>' +
//                     '<button type="submit" id="submitButton"> </button> ' +
//                 '</form>' +
//                 '</td>' +
//                 '</tr>';
//
//             $("#activity-container").append(tableRow)
//         }));
// }
//
// $('body').on('click', 'button.delete-btn', function () {
//     let childId = $(this).data('child-id');
//     let activityName = $(this).data('activity-name');
//
//     fetch('http://localhost:8080/activity/remove', {method: 'DELETE', credentials: 'include'}).then(_ => reloadParents())
// });