const handler = (table, id) => {
    if (table == "department") updateData("employee", `departmentId=${id}`)
    else if (table == "employee") updateData("number", `employeeId=${id}`)
}

const updateData = (table, query) => {
    let keys = []
    if (table == "department") keys = ["id", "title", "number"]
    else if (table == "employee") keys = ["id", "name"]
    else if (table == "number") keys = ["id", "type", "number"]

    let req = new Request(`ajax/${table}?${query}`)
    fetch(req).then(response => response.json()).then(data => {
        ReactDOM.render(
            React.createElement(Table, { "data": data, "table": table, "keys": keys, "query": query }, null),
            document.getElementById(`${table}_table_container`)
        );
    })

    if (table == "department") {
        ReactDOM.render(
            React.createElement("div"),
            document.getElementById("employee_table_container")
        );
    }
    if (table != "number") {
        ReactDOM.render(
            React.createElement("div"),
            document.getElementById("number_table_container")
        );
    }
}

ReactDOM.render(
    React.createElement(Form, {
        "forms": {
            "department": { "id": null, "title": "", "number": "" },
            "employee": { "id": null, "name": "", "departmentId": null },
            "number": { "id": null, "type": "", "number": "", "employeeId": null }
        }
    }),
    document.getElementById("form_container")
);
ReactDOM.render(
    React.createElement(SearchForm, {
        "forms": {
            "department": { "id": null, "title": "", "number": "" },
            "employee": { "id": null, "name": "", "departmentId": null },
            "number": { "id": null, "type": "", "number": "", "employeeId": null }
        }
    }),
    document.getElementById("search_container")
);

updateData("department")