import {useParams} from "react-router-dom";
import UserDetails from "../components/UserDetails";

const UserDetailsPage = () => {
    const {role, id} = useParams();

    return (
        <>
            <UserDetails id ={id} role={role}/>
        </>
    )
}

export default UserDetailsPage