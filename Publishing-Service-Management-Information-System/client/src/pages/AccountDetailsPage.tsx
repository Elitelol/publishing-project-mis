import {useContext} from "react";
import {UserContext} from "../auth";
import UserDetails from "../components/UserDetails";

const AccountDetailsPage = () => {
    const [context, setContext] = useContext(UserContext);
    const id = context.data?.id;

    return <UserDetails id ={id} role={null}/>
}

export default AccountDetailsPage;