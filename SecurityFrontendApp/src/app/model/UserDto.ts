

export class UserDto{
    id:string;
    mail:string;
    name:string;
    country:string;
    organizationUnit:string;

    constructor(id:string,
        mail:string,
        name:string,
        country:string,
        organizationUnit:string){
            this.id=id;
            this.mail=mail;
            this.name=name;
            this.country=country;
            this.organizationUnit=organizationUnit;
        }
}