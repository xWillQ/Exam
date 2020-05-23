class Form extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            fields: {
                login: "",
                pass: "",
                res: "",
                role: "",
                ds: "",
                de: "",
                vol: ""
            }
        }
        this.changeHandler = this.changeHandler.bind(this)
        this.send = this.send.bind(this)
    }

    changeHandler(event) {
        this.state.fields[event.target.name] = event.target.value
    }

    send() {
        let req = new Request("ajax/activity", { method: "POST", body: JSON.stringify(this.state.fields) })
        fetch(req).then(response => response.text()).then(msg => {
            if (msg == 0) {
                let table = document.getElementById("activity_table")
                updateData("activity", table.getAttribute("query"))
            }
        })
    }

    renderFields() {
        let fields = Object.keys(this.state.fields)
        return fields.map((value) => {
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
