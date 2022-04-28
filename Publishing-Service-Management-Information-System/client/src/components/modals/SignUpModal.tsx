import {Modal, Button, InputGroup, FormControl, Form} from "react-bootstrap";
import {useEffect, useState} from "react";
import axios from "axios";
import Role from "../../models/ConfigModels";

interface UserResponse {
    message: String
}

const SignUpModalComponent = () => {

    const [show, setShow] = useState<boolean>(false);
    const [username, setUsername] = useState<string>("");
    const [password, setPassword] = useState<string>("");
    const [email, setEmail] = useState<string>("");
    const [firstName, setFirstName] = useState<string>("");
    const [lastName, setLastName] = useState<string>("");
    const [role, setRole] = useState<string>("Author");
    const [roleSelection, setRoleSelection] = useState<Role[]>([]);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    const handleClick = async () => {
        const response = await axios.post<UserResponse>("http://localhost:8080/publishing-app/access/signUp", {
            username,
            password,
            firstName,
            lastName,
            email,
            role
        })
        console.log(response.data.message)
    }

    useEffect(() => {
        axios.get<Role[]>("http://localhost:8080/publishing-app/type/roles").then(response => setRoleSelection(response.data));
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
                            {
                                roleSelection.map((value) => {
                                    return <option value={value.role}>{value.role}</option>
                                })
                            }
                        </Form.Select>
                    </InputGroup>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant = "secondary" onClick ={handleClose}> Close </Button>
                    <Button variant = "primary" onClick = {handleClick}> Sign Up</Button>
                </Modal.Footer>
            </Modal>
        </>
    )
}

export default SignUpModalComponent;