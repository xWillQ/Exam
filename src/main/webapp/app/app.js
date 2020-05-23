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

// ReactDOM.render(
//     React.createElement(Form),
//     document.getElementById("form_container")
// );

updateData("department")