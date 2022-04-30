import {useEffect, useState} from "react";
import axios from "axios";
import Role from "../../models/ConfigModels";
import ApiUrl from "../../config/api.config";

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
        try {
            await axios.post<UserResponse>(ApiUrl() + "access/signUp", {
                username,
                password,
                firstName,
                lastName,
                email,
                role
            })
            setUsername("");
            setPassword("");
            setFirstName("");
            setLastName("");
            setEmail("");
            setRole("");
        } catch (error) {

        }
    }
    useEffect(() => {
        axios.get<Role[]>("http://localhost:8080/publishing-app/type/roles").then(response => setRoleSelection(response.data));
    }, [])

    return(
        <>
        </>
    )
}

export default SignUpModalComponent;