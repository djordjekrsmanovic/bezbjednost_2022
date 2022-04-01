export class InformationData {

    userID: string;
    email: string;
    country: string;
    organizationUnit: string;
    organizationName: string;
    givenName: string;
    surname: string;
    commonName: string;


    constructor(userID: string,
                email: string,
                country: string,
                organizationUnit: string,
                organizationName: string,
                givenName: string,
                surname: string,
                commonName: string)
    {
        this.userID = userID;
        this.email = email;
        this.country = country;
        this.organizationUnit = organizationUnit;
        this.organizationName = organizationName;
        this.givenName = givenName;
        this.surname = surname;
        this.commonName = commonName;
    }

   
}