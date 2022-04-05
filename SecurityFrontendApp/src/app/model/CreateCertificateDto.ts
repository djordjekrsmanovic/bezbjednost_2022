
export class CreateCertificateDto{
    constructor(
        public issuerMail:string='',
        public issuerCertificateSerialNumber:string='',
        public issuerCertificateType:string='',
        public subjectMail:string='',
        public startDate:Date=new Date(),
        public endDate:Date=new Date(),
        public keyUsages:string[]=[],
        public  extendedKeyUsages:string[]=[],
        public subjectCertificateType:string=''
    ){

    }
}

