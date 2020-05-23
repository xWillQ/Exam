class Form extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            fields: props.forms,
            table: props.table,
            action: props.action
        }
        this.changeHandler = this.changeHandler.bind(this)
        this.send = this.send.bind(this)
    }

    changeHandler(event) {
        this.state.fields[event.target.name] = event.target.value
    }

    send() {
        let req = new Request(`ajax/${this.state.table}?${this.state.action}`, { method: "POST", body: JSON.stringify(this.state.fields) })
        fetch(req).then(response => response.text()).then(msg => {
            let table = document.getElementById(`${this.state.table}_table`)
            updateData(this.state.table, table.getAttribute("query"))
            // if (msg == 0) {
            // }
        })
    }

    renderFields() {
        let fields = Object.keys(this.state.fields)
        return fields.map((value) => {
            if (this.state.action == "delete" && value != "id") return
            if (this.state.action == "add" && value == "id") return
            if (this.state.action == "update" && value == "employeeId") return
            return React.createElement("div", { id: "input_container" }, [value, React.createElement("input", { id: "input_field", type: "text", name: value, onChange: this.changeHandler })])
        })
    }

    render() {
        return (
            React.createElement("form", null,
                [
                    this.renderFields(),
                    React.createElement("input", { type: "button", id: "submit-button", value: "Submit", onClick: this.send })
                ]
            )

        );
    }

}
