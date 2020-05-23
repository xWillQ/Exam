class Form extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            fields: {
                "department": { "id": null, "title": "", "number": "" },
                "employee": { "id": null, "name": "", "departmentId": null },
                "number": { "id": null, "type": "INTERNAL_CELL", "number": "", "employeeId": null }
            },
            table: "department",
            action: "add",
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

        if (target.id == "action_list") state.action = target.value
        else if (target.id == "table_list") state.table = target.value
        else {
            let value = target.value
            if (target.name.toLowerCase().includes("id")) {
                if (value == "") state.fields[state.table][target.name] = null
                else if (!isNaN(value)) state.fields[state.table][target.name] = value
            }
            else state.fields[state.table][target.name] = value
        }
        this.setState(state)
    }

    send() {
        let req = new Request(`ajax/${this.state.table}?${this.state.action}`, { method: "POST", body: JSON.stringify(this.state.fields[this.state.table]) })
        fetch(req).then(() => {
            let table = document.getElementById(`${this.state.table}_table`)
            updateData(this.state.table, table.getAttribute("query"))
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
            if (this.state.action == null) return
            if (this.state.action == "delete" && value != "id") return
            if (this.state.action == "add" && value == "id") return
            if (this.state.action == "update" && value == "employeeId") return
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
                    React.createElement("select", { id: "action_list", onChange: this.changeHandler }, [
                        React.createElement("option", { value: "add" }, "Add"),
                        React.createElement("option", { value: "delete" }, "Delete"),
                        React.createElement("option", { value: "update" }, "Update")
                    ]),
                    React.createElement("select", { id: "table_list", onChange: this.changeHandler }, [
                        React.createElement("option", { value: "department" }, "Department"),
                        React.createElement("option", { value: "employee" }, "Employee"),
                        React.createElement("option", { value: "number" }, "Number")
                    ]),
                    this.renderFields(),
                    React.createElement("input", { type: "button", id: "submit-button", value: "Submit", onClick: this.send })
                ]
            )

        );
    }

}
