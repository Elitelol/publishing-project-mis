import {useState} from "react";
import Role from "../models/Role";
import axios from "axios";
import ApiUrl from "../config/api.config";

interface UserResponse {
    message: String
}

const SignUp = () => {

    const [username, setUsername] = useState<string>("");
    const [password, setPassword] = useState<string>("");
    const [email, setEmail] = useState<string>("");
    const [firstName, setFirstName] = useState<string>("");
    const [lastName, setLastName] = useState<string>("");
    const [role, setRole] = useState<string>("Author");
    const [roleSelection, setRoleSelection] = useState<Role[]>([]);

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

    return(
        <div>
            SignUp
        </div>
    );
}

export default SignUp;