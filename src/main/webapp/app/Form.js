class Form extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            fields: props.forms,
            table: "department",
            action: "add"
        }
        this.changeHandler = this.changeHandler.bind(this)
        this.send = this.send.bind(this)
    }

    changeHandler(event) {
        if (event.target.id == "action_list") this.state.action = event.target.value
        else if (event.target.id == "table_list") this.state.table = event.target.value
        else this.state.fields[this.state.table][event.target.name] = event.target.value
        this.setState(this.state)
        return
    }

    send() {
        let req = new Request(`ajax/${this.state.table}?${this.state.action}`, { method: "POST", body: JSON.stringify(this.state.fields[this.state.table]) })
        fetch(req).then(response => response.text()).then(msg => {
            let table = document.getElementById(`${this.state.table}_table`)
            updateData(this.state.table, table.getAttribute("query"))
            // if (msg == 0) {
            // }
        })
    }

    renderFields() {
        let fields = Object.keys(this.state.fields[this.state.table])
        return fields.map((value) => {
            if (this.state.action == null) return
            if (this.state.action == "delete" && value != "id") return
            if (this.state.action == "add" && value == "id") return
            if (this.state.action == "update" && value == "employeeId") return
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
                    React.createElement("select", { id: "action_list", onChange: this.changeHandler }, [
                        React.createElement("option", { value: "add" }, "Add"),
                        React.createElement("option", { value: "delete" }, "Delete"),
                        React.createElement("option", { value: "update" }, "Update")
                    ]),
                    this.renderFields(),
                    React.createElement("input", { type: "button", id: "submit-button", value: "Submit", onClick: this.send })
                ]
            )

        );
    }

}
