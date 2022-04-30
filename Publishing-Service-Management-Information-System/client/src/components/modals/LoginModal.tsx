import {Button, FormControl, InputGroup, Modal} from "react-bootstrap";
import {useContext, useState} from "react";
import axios from "axios";
import {useNavigate} from "react-router-dom";
import {UserContext} from "../../auth";
import ApiUrl from "../../config/api.config";

const LoginModalComponent = () => {

    interface loginResponse {
        token: string,
        id: number,
        role: string
    }

    const [show, setShow] = useState<boolean>(false);
    const [username, setUsername] = useState<string>("");
    const [password, setPassword] = useState<string>("");
    const navigate = useNavigate();
    const [context, setContext] = useContext(UserContext);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    const handleClick = async () => {
        try {
            const response = await axios.post<loginResponse>(ApiUrl() + "access/login", {
                username,
                password
            });
            setContext({data:{id: response.data.id, role: response.data.role }, loading: false, error: null})
            localStorage.setItem("token", response.data.token);
            axios.defaults.headers.common["auth"] = `Bearer ${response.data.token}`;
            navigate("/dashboard");
        } catch (error) {

        }
    }

    return(
        <>
            <Button variant = "danger" style = {{marginRight: "1rem", padding: "1rem, 4rem"}} onClick = {handleShow}>Login</Button>
            <Modal show = {show} onHide = {handleClose}>
                <Modal.Header>Login</Modal.Header>
                <Modal.Body>
                    <InputGroup className = "mb-3">
                        <InputGroup.Text>Username</InputGroup.Text>
                        <FormControl value={username} onChange = {event => setUsername(event.target.value)} />
                    </InputGroup>
                    <InputGroup className = "mb-3">
                        <InputGroup.Text>Password</InputGroup.Text>
                        <FormControl type="password" value = {password} onChange = {event => setPassword(event.target.value)}/>
                    </InputGroup>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant = "secondary" onClick ={handleClose}> Close </Button>
                    <Button variant = "primary" onClick = {handleClick}>Login</Button>
                </Modal.Footer>
            </Modal>
        </>
    )
};

export default LoginModalComponent