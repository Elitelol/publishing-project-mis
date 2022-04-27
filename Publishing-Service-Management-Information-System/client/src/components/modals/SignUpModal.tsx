import {Modal, Button, InputGroup, FormControl, Form} from "react-bootstrap";
import {useEffect, useState} from "react";
import axios from "axios";
import Role from "../../models/ConfigModels";

const SignUpModalComponent = () => {

    type RoleResponse = {
        data: Role[]
    }

    const [show, setShow] = useState(false);
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [email, setEmail] = useState("");
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [role, setRole] = useState("Author");
    const [roleSelection, setRoleSelection] = useState([]);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    const handleClick = () => {
        axios.post("http://localhost:8080/publishing-app/access/signUp")
    }

    useEffect(() => {
        axios.get("http://localhost:8080/publishing-app/type/roles").then(response => {
            setRoleSelection(response.data)
        })
        console.log(roleSelection);
    }, [])

    return(
        <>
            <Button onClick = {handleShow}>Sign Up</Button>
            <Modal show = {show} onHide ={handleClose}>
                 <Modal.Header>
                     <Modal.Title>Sign Up</Modal.Title>
                 </Modal.Header>
                <Modal.Body>
                    <InputGroup className = "mb-3">
                        <InputGroup.Text>Username</InputGroup.Text>
                        <FormControl value = {username} onChange = {event => setUsername(event.target.value)}/>
                    </InputGroup>
                    <InputGroup className = "mb-3">
                        <InputGroup.Text>Password</InputGroup.Text>
                        <FormControl type="password" value = {password} onChange = {event => setPassword(event.target.value)}/>
                    </InputGroup>
                    <InputGroup className = "mb-3">
                        <InputGroup.Text>Email</InputGroup.Text>
                        <FormControl type = "email" value = {email} onChange = {event => setEmail(event.target.value)}/>
                    </InputGroup>
                    <InputGroup className = "mb-3">
                        <InputGroup.Text>First Name</InputGroup.Text>
                        <FormControl value = {firstName} onChange = {event => setFirstName(event.target.value)}/>
                    </InputGroup>
                    <InputGroup className = "mb-3">
                        <InputGroup.Text>Last Name</InputGroup.Text>
                        <FormControl value = {lastName} onChange = {event => setLastName(event.target.value)}/>
                    </InputGroup>
                    <InputGroup className = "mb-3">
                        <InputGroup.Text>Role</InputGroup.Text>
                        <Form.Select value = {role} onChange = {event => setRole(event.target.value)}>
                            {roleSelection.map((roles, index) => {
                                return <option> {roles} </option>
                            })};
                        </Form.Select>
                    </InputGroup>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant = "secondary" onClick ={handleClose}> Close </Button>
                    <Button variant = "primary">Sign Up</Button>
                </Modal.Footer>
            </Modal>
        </>
    )
}

export default SignUpModalComponent;