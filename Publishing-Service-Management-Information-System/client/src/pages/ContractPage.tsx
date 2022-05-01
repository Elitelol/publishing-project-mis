import {useParams} from "react-router-dom";
import {Container} from "@mui/material";
import NavigationGroup from "../components/NavigationGroup";

const ContractPage = () => {
    const {id} = useParams()

    return(
        <Container>
            <NavigationGroup id = {id}/>
        </Container>
    )
}

export default ContractPage