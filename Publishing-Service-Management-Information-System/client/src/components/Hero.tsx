import styled from "styled-components";
import {Container} from "react-bootstrap";
import ModalComponent from "./modals/Modal";

const HeroComponent = styled.header`
   padding: 5rem, 0;
   height: 60vh;
   background-image: url("https://images.unsplash.com/photo-1634621388916-da61c670ffac?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=880&q=80");
   background-size: cover;
   background-position: center;
`;

const HeaderContainer = styled.div`
    background-color: rgb(5, 148, 112);
    padding: 3rem;
    color: white;
    width: 32.5rem;
`;

const Heading = styled.h1`
    font-size: 5rem;
`;

const SubHeading = styled.h3`
    margin: 1rem, 0;
    font-weight: 400;
`;

const Hero = () => {
   return <HeroComponent>
       <Container>
           <HeaderContainer>
               <Heading>
                   Publishing n stuff
                   <SubHeading>
                       Start making publications
                   </SubHeading>
               </Heading>
               <ModalComponent/>
           </HeaderContainer>
       </Container>
   </HeroComponent>
}

export default Hero