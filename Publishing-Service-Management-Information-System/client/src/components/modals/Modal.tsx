import {Modal, Button, InputGroup, FormControl, Form} from "react-bootstrap";
import {useState} from "react";

const ModalComponent = () => {

    const [show, setShow] = useState(false);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

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
                        <FormControl/>
                    </InputGroup>
                    <InputGroup className = "mb-3">
                        <InputGroup.Text>Password</InputGroup.Text>
                        <FormControl type="password"/>
                    </InputGroup>
                    <InputGroup className = "mb-3">
                        <InputGroup.Text>Email</InputGroup.Text>
                        <FormControl type = "email"/>
                    </InputGroup>
                    <InputGroup className = "mb-3">
                        <InputGroup.Text>First Name</InputGroup.Text>
                        <FormControl/>
                    </InputGroup>
                    <InputGroup className = "mb-3">
                        <InputGroup.Text>Last Name</InputGroup.Text>
                        <FormControl/>
                    </InputGroup>
                    <InputGroup className = "mb-3">
                        <InputGroup.Text>Role</InputGroup.Text>
                        <Form.Select>
                            <option> test</option>
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

export default ModalComponent;