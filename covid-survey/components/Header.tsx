import { Box, Heading} from "native-base";

export default function Header() {
    return (
        <Box p="6" py="10" w="90%" >
          <Heading size="lg" fontWeight="600" color="coolGray.800"
              _dark={{color: "warmGray.50"}}>
              COVID-19 TEST SURVEY
          </Heading>
          <Heading mt="1" _dark={{ color: "warmGray.200"}}
              color="coolGray.600" fontWeight="medium" size="xs">
              Please fill in the form to continue!
          </Heading>
        </Box>
    );
}