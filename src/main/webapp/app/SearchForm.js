class SearchForm extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            fields: {
                "department": { "id": null, "title": "", "number": "" },
                "employee": { "id": null, "name": "", "departmentId": null },
                "number": { "id": null, "type": "INTERNAL_CELL", "number": "", "employeeId": null }
            },
            table: "department",
            numberTypes: null
        }
        fetch(new Request(`ajax/number?types`)).then(response => response.json()).then(data => {
            this.state.numberTypes = data
        })
        this.changeHandler = this.changeHandler.bind(this)
        this.send = this.send.bind(this)
    }

    changeHandler(event) {
        let state = this.state
        let target = event.target

        if (target.id == "table_list") state.table = target.value
        else {
            let value = target.value
            if (target.name.toLowerCase().includes("id")) {
                if (value == "") state.fields[state.table][target.name] = null
                else if (!isNaN(value)) state.fields[state.table][target.name] = value
            }
            else state.fields[state.table][target.name] = value
        }
        this.setState(state)
        return
    }

    send() {
        let req = new Request(`ajax/${this.state.table}?search`, { method: "POST", body: JSON.stringify(this.state.fields[this.state.table]) })
        fetch(req).then(response => response.json()).then(data => {
            let keys = []
            if (this.state.table == "department") keys = ["id", "title", "number"]
            else if (this.state.table == "employee") keys = ["id", "name"]
            else if (this.state.table == "number") keys = ["id", "type", "number"]
            ReactDOM.render(
                React.createElement("div"),
                document.getElementById("search_table_container")
            );
            ReactDOM.render(
                React.createElement(Table, { "data": data, "table": this.state.table, "keys": keys, "query": "" }),
                document.getElementById("search_table_container")
            );
        })
    }

    renderNumberTypes() {
        return this.state.numberTypes.map(value => {
            return React.createElement("option", { "value": value }, value)
        })
    }

    renderFields() {
        let fields = Object.keys(this.state.fields[this.state.table])
        return fields.map((value) => {
            if (this.state.table == "number" && value == "type") {
                return React.createElement("div", { id: "input_container" },
                    [
                        value,
                        React.createElement("select", { id: "input_field", name: value, onChange: this.changeHandler }, [this.renderNumberTypes()]),
                    ]
                )
            }

            let prevText = this.state.fields[this.state.table][value]
            if (prevText == null) prevText = ""
            return React.createElement("div", { id: "input_container" },
                [
                    value,
                    React.createElement("input", { id: "input_field", type: "text", name: value, "value": prevText, onChange: this.changeHandler })
                ]
            )
        })
    }

    render() {
        return (
            React.createElement("form", null,
                [
                    React.createElement("select", { id: "table_list", onChange: this.changeHandler }, [
                        React.createElement("option", { value: "department" }, "Departments"),
                        React.createElement("option", { value: "employee" }, "Employee"),
                        React.createElement("option", { value: "number" }, "Number")
                    ]),
                    this.renderFields(),
                    React.createElement("input", { type: "button", id: "submit-button", value: "Filter", onClick: this.send })
                ]
            )

        );
    }

}
