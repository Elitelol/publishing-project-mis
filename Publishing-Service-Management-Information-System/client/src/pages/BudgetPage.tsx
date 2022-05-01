import {Container} from "@mui/material";
import NavigationGroup from "../components/NavigationGroup";
import {useParams} from "react-router-dom";

const BudgetPage = () => {

    const {id} = useParams()

   return(
       <Container>
           <NavigationGroup id = {id}/>
       </Container>
   )
}

export default BudgetPage