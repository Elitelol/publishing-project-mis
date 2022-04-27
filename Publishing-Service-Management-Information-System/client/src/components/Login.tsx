import {useState} from "react";

const Login = () => {
    const [username, setUsername] = useState<string>("");
    const [password, setPassword] = useState<string>("");

    return(
        <div>
            Login
        </div>
    );
}

export default Login;