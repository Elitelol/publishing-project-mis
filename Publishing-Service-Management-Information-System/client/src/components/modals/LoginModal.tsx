import {Button, FormControl, InputGroup, Modal} from "react-bootstrap";
import {useState} from "react";

const LoginModalComponent = () => {

    const [show, setShow] = useState(false);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    return(
        <>
            <Button variant = "danger" style = {{marginRight: "1rem", padding: "1rem, 4rem"}} onClick = {handleShow}>Login</Button>
            <Modal show = {show} onHide = {handleClose}>
                <Modal.Header>Login</Modal.Header>
                <Modal.Body>
                    <InputGroup className = "mb-3">
                        <InputGroup.Text>Username</InputGroup.Text>
                        <FormControl/>
                    </InputGroup>
                    <InputGroup className = "mb-3">
                        <InputGroup.Text>Password</InputGroup.Text>
                        <FormControl type="password"/>
                    </InputGroup>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant = "secondary" onClick ={handleClose}> Close </Button>
                    <Button variant = "primary">Login</Button>
                </Modal.Footer>
            </Modal>
        </>
    )
};

export default LoginModalComponent