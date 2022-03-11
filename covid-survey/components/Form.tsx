import { Box, Text, VStack, FormControl, Input, Button, HStack, Radio, Select, Container} from "native-base";
import {useEffect, useState} from 'react';
import React, { Component } from 'react'
import {db} from "../../db/firestore";

export default function Form() {

    const [sendOpen, setSendOpen] = useState(true);
    const [hadVaccine, setHadVaccine] = useState(false);
    const [hadThird, setHadThird] = useState(false);
    const [name, setName] = useState("");
    const [city, setCity] = useState("");
    const [gender, setGender] = useState("0");
    const [appliedVacc, setAppliedVacc] = useState("0");
    const [birthdate, setBirthdate] = useState("")

    const createParticipant = async () => {
        db.collection("participants").add({
            hadVaccine: hadVaccine,
            hadThird: hadThird,
            name: name,
            city: city,
            gender: gender,
            appliedVacc: appliedVacc,
            birthdate: birthdate
        });
    };

    return (
        <VStack w="50%" h="100%" space={10} mt="10">
            <FormControl>
                <FormControl.Label>Name Surname</FormControl.Label>
                <Input value={name} onChangeText={value => {setName(value);}}/>
            </FormControl>
              <FormControl>
                <FormControl.Label>Birthdate</FormControl.Label>
                <Input placeholder="dd/mm/yyyy" onChangeText={value => {setBirthdate(value);}}/>
              </FormControl>
              <FormControl>
                <FormControl.Label>City</FormControl.Label>
                <Input value={city} onChangeText={value => {setCity(value);}}/>
              </FormControl>
              <FormControl>
                <FormControl.Label>Gender</FormControl.Label>
                <Radio.Group name="gender" defaultValue={gender}
                    onChange={value => {setGender(value || "0");}}>
                  <Radio value="-1" my="1">
                    Male
                  </Radio>
                  <Radio value="1" my="1">
                    Female
                  </Radio>
                  <Radio value="0" my="1">
                    Non-binary
                  </Radio>
                </Radio.Group>
              </FormControl>


              <FormControl>
                <FormControl.Label>Have you had Covid-19 vaccination before?</FormControl.Label>
                <Radio.Group
                    onChange={value => {setHadVaccine(value == "1" ? true : false);}}>
                  <Radio value="1" my="1">
                    Yes
                  </Radio>
                  <Radio value="-1" my="1">
                    No
                  </Radio>
                </Radio.Group>
              </FormControl>
              {hadVaccine && (
                  <Box >

                      <FormControl>
                          <FormControl.Label>Choose Applied Vaccine Type:</FormControl.Label>
                          <Select placeholder="Choose Vaccine Type" value={appliedVacc}
                                  onChange={value => {setAppliedVacc(value);}}>
                              <Select.Item label="BionTech" value="1" />
                              <Select.Item label="MODERNA" value="2" />
                              <Select.Item label="Johnson & Johnson" value="3" />
                              <Select.Item label="Astra Zenica" value="4" />
                          </Select>
                      </FormControl>

                      <FormControl mt="10">
                        <FormControl.Label>Have you experienced any side effects from Covid-19 vaccination? Please explain.</FormControl.Label>
                        <Input />
                      </FormControl>
                      <FormControl mt="10">
                        <FormControl.Label>Have you had your 3rd Covid-19 vaccination?</FormControl.Label>
                        <Radio.Group
                            onChange={value => {setHadThird(value == "1" ? true : false);}}>
                          <Radio value="1" my="1">
                            Yes
                          </Radio>
                          <Radio value="-1" my="1">
                            No
                          </Radio>
                        </Radio.Group>
                      </FormControl>
                      {hadThird && (
                        <FormControl mt="10">
                          <FormControl.Label>Have you had any PCR positive cases or Covid-19 symptoms after 3rd vaccination? Please explain.</FormControl.Label>
                          <Input />
                        </FormControl>
                      )}
                  </Box>
              )}

              {sendOpen && (
                  <Box>
                      <button onClick={createParticipant} >Send</button>
                  {/*<Button onClick={createParticipant} mt="2" colorScheme="indigo">*/}
                  {/*  Send*/}
                  {/*</Button>*/}
                  <HStack mt="6" justifyContent="center">
                    <Text fontSize="sm" color="coolGray.600" _dark={{
                    color: "warmGray.200"
                  }}>
                      The form is ready to send!
                    </Text>
                  </HStack>
                  </Box>
              )}

        </VStack>
    );
}