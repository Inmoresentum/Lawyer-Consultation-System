package com.cse471.project.views.article;


import com.cse471.project.security.AuthenticatedUser;
import com.cse471.project.views.MainLayout;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;



@Route(value = "article", layout = MainLayout.class)
@AnonymousAllowed

public class ArticleView extends VerticalLayout {


    public ArticleView(){

        addClassName("article-view-main");
        Section mySection = new Section();
        mySection.addClassName("article-view-section");
        H1 title = new H1("Welcome to Article Page!");
        title.addClassName("article-view-page-h1");
        H2 title1 = new H2("Marriage Divorce");
        title1.addClassName("article-view-page-h2");
        mySection.add(title1);
        Paragraph mypParagraph1 = new Paragraph();
        mypParagraph1.addClassName("article-view-page-h2");
        mypParagraph1.setText("If you want to apply for divorce in Bangladesh through a lawyer, you can follow these steps:\n" +
                "\n" +
                "Find a Lawyer: Find a lawyer who specializes in family law and has experience in divorce cases. You can ask for recommendations from family, friends, or other legal professionals.\n" +
                "\n" +
                "Meet with the Lawyer: Set up a meeting with the lawyer to discuss your situation and to get an understanding of the divorce process. During the meeting, provide details about your marriage and the reasons for the divorce.\n" +
                "\n" +
                "Provide Required Documents: Provide the lawyer with necessary documents, such as marriage certificate, proof of property ownership, and any other relevant documents related to the marriage.\n" +
                "\n" +
                "Draft Divorce Petition: The lawyer will draft a divorce petition on your behalf, which will outline the reasons for divorce and the relief you are seeking.\n" +
                "\n" +
                "Submit Petition to Court: Once the petition is drafted and signed, the lawyer will file it with the family court in the district where you and your spouse reside. You will need to pay a court fee for the filing of the petition.\n" +
                "\n" +
                "Attend Court Hearings: You and your lawyer will attend court hearings as required. The court may also ask you to attend counseling sessions to try to resolve the issues before a divorce is granted.\n" +
                "\n" +
                "Finalize the Divorce: If the court approves the divorce, you will receive a divorce decree. The decree will outline the terms of the divorce, such as custody arrangements, alimony, and division of property.\n" +
                "\n" +
                "It's important to note that divorce proceedings can be complex and emotional. It's always advisable to seek legal advice and assistance to ensure that your rights and interests are protected throughout the process.\n");
        mySection.add(mypParagraph1);


        H2 title2 = new H2("Tax evasion");
        title2.addClassName("article-view-page-h2");
        mySection.add(title2);
        Paragraph mypParagraph2 = new Paragraph();
        mypParagraph2.addClassName("article-view-page-h2");
        mypParagraph2.setText("Tax evasion is a serious crime that involves deliberately underreporting or failing to report taxable income to the government. The legal procedure for tax evasion may vary depending on the country or jurisdiction in question. Here's an overview of the procedure in general:\n" +
                "\n" +
                "Investigation: The government or tax authorities will initiate an investigation into the taxpayer's financial records to determine if there is any evidence of tax evasion.\n" +
                "\n" +
                "Assessment: If the authorities find evidence of tax evasion, they will assess the amount of tax that was not paid and any penalties or interest due.\n" +
                "\n" +
                "Notification: The taxpayer will receive a notification of the assessment, which will include the amount owed, the deadline for payment, and any appeal rights.\n" +
                "\n" +
                "Payment or Appeal: The taxpayer can either pay the assessed amount or file an appeal if they believe the assessment is incorrect or unfair.\n" +
                "\n" +
                "Court Proceedings: If the taxpayer does not pay or file an appeal, the authorities may initiate court proceedings against them. The taxpayer will have the opportunity to present their case in court and defend themselves against the charges of tax evasion.\n" +
                "\n" +
                "Conviction and Sentencing: If the court finds the taxpayer guilty of tax evasion, they may be convicted and sentenced to pay fines or serve time in prison, depending on the severity of the offense.\n" +
                "\n" +
                "Tax evasion is a serious crime and can have severe consequences, including financial penalties, criminal charges, and even imprisonment. It's always advisable to report all taxable income accurately and in a timely manner to avoid any potential legal issues.\n" +
                "\n");
        mySection.add(mypParagraph2);


        H2 title3 = new H2("Child adoption");
        title3.addClassName("article-view-page-h2");
        mySection.add(title3);
        Paragraph mypParagraph3 = new Paragraph();
        mypParagraph3.addClassName("article-view-page-h2");
        mypParagraph3.setText("The legal procedure for child adoption in Bangladesh involves several steps and is governed by the Adoption Rules of 2018. Here's an overview of the process:\n" +
                "\n" +
                "Eligibility: The adopting parents must meet certain eligibility criteria, such as being over 25 years old, being of sound mind and character, and having no criminal record.\n" +
                "\n" +
                "Application: The adopting parents must submit an application to the Department of Social Services or an authorized adoption agency, along with all the necessary documents, including their birth certificates, marriage certificate, income certificate, and a medical certificate.\n" +
                "\n" +
                "Home Study: The adopting parents must undergo a home study conducted by a social worker to assess their suitability for adoption. The social worker will visit their home, interview them, and check their background.\n" +
                "\n" +
                "Child Referral: The Department of Social Services or the adoption agency will refer a child to the adopting parents based on their eligibility and preference. The child must be legally free for adoption and not have any living biological parents or guardians who can take care of them.\n" +
                "\n" +
                "Pre-Adoption Care: The adopting parents will have to take care of the child in pre-adoption care for at least two weeks to assess the child's compatibility with the family and to establish a bond with the child.\n" +
                "\n" +
                "Court Approval: The adopting parents will have to file a petition in court for the adoption of the child. The court will verify that all legal requirements have been fulfilled and that the adoption is in the best interest of the child. If everything is in order, the court will approve the adoption and issue an adoption order.\n" +
                "\n" +
                "Post-Adoption Follow-up: The Department of Social Services or the adoption agency will conduct follow-up visits and assessments to ensure the well-being and welfare of the adopted child.\n" +
                "\n" +
                "Adoption in Bangladesh is a complex and lengthy process, and it's always advisable to seek legal guidance and assistance to ensure that all legal requirements are fulfilled.\n" +
                "\n");
        mySection.add(mypParagraph3);


        H2 title4 = new H2("Children custody after divorce");
        title4.addClassName("article-view-page-h2");
        mySection.add(title4);
        Paragraph mypParagraph4 = new Paragraph();
        mypParagraph4.addClassName("article-view-page-h2");
        mypParagraph4.setText("In Bangladesh, the legal procedure for applying for child custody after divorce is governed by the Family Courts Ordinance of 1985. Here's an overview of the process:\n" +
                "\n" +
                "Filing a Petition: The parent who wishes to obtain custody of the child must file a petition in the family court of the district where the child resides. The petition must include details such as the parent's relationship with the child, the reasons for seeking custody, and any other relevant information.\n" +
                "\n" +
                "Service of Notice: The court will issue a notice to the other parent, informing them of the custody petition and the date of the hearing. The other parent will have the opportunity to file a response to the petition and to attend the hearing.\n" +
                "\n" +
                "Evidence and Testimony: The court will hear evidence and testimony from both parents and any witnesses they may have. The court may also consider the child's wishes if they are of a sufficient age and maturity to express a preference.\n" +
                "\n" +
                "Best Interests of the Child: The court will consider the best interests of the child when making a custody determination. Factors that the court may consider include the child's age, health, and welfare, as well as each parent's ability to care for the child.\n" +
                "\n" +
                "Custody Order: If the court determines that one parent should have custody, it will issue a custody order specifying the terms of the custody arrangement, including visitation rights for the other parent.\n" +
                "\n" +
                "Compliance: Both parents must comply with the terms of the custody order. If either parent fails to comply, the other parent may file a motion for contempt of court.\n" +
                "\n" +
                "\n");
        mySection.add(mypParagraph4);


        H2 title5 = new H2("Murder case");
        title5.addClassName("article-view-page-h2");
        mySection.add(title5);
        Paragraph mypParagraph5 = new Paragraph();
        mypParagraph5.addClassName("article-view-page-h2");
        mypParagraph5.setText("In Bangladesh, murder is a serious crime punishable under the Penal Code of 1860. The legal way of punishing a murder involves several steps, which include:\n" +
                "\n" +
                "Investigation: The police or law enforcement agency will initiate an investigation into the murder, which will involve collecting evidence, interviewing witnesses, and building a case against the suspect.\n" +
                "\n" +
                "Arrest: If the investigation leads to a suspect, the police will arrest them and bring them to trial.\n" +
                "\n" +
                "Charge: The suspect will be charged with murder under the Penal Code and will have the opportunity to plead guilty or not guilty.\n" +
                "\n" +
                "Trial: The case will go to trial in a court of law, where the prosecution will present evidence and witnesses to prove the suspect's guilt beyond a reasonable doubt.\n" +
                "\n" +
                "Verdict: The judge or jury will reach a verdict based on the evidence presented at trial.\n" +
                "\n" +
                "Sentencing: If the suspect is found guilty, they will be sentenced according to the Penal Code, which may include life imprisonment or the death penalty.\n" +
                "\n" +
                "It's always advisable to seek legal advice and assistance to ensure that your rights and interests are protected. Additionally, the death penalty is a controversial issue, and its use is subject to debate and international scrutiny.\n");
        mySection.add(mypParagraph5);


        H2 title6 = new H2("Domestic violence on women");
        title6.addClassName("article-view-page-h2");
        mySection.add(title6);
        Paragraph mypParagraph6 = new Paragraph();
        mypParagraph6.addClassName("article-view-page-h2");
        mypParagraph6.setText("In Bangladesh, domestic violence is a criminal offense under the Domestic Violence (Prevention and Protection) Act of 2010. If you are a victim of domestic violence, here are some legal steps you can take to fight it in court:\n" +
                "\n" +
                "File a Complaint: The first step in fighting domestic violence in court is to file a complaint with the police or a magistrate. You will need to provide details of the abuse, including dates, times, and any evidence you may have, such as photographs or medical reports.\n" +
                "\n" +
                "Protection Order: If the court finds that you are at risk of further harm, it may issue a protection order that prohibits the abuser from contacting or coming near you or your family members.\n" +
                "\n" +
                "Evidence Collection: You will need to collect evidence to support your case, such as medical reports, photographs of injuries, and witness statements.\n" +
                "\n" +
                "Legal Representation: It's advisable to seek legal representation from a lawyer who has experience in domestic violence cases.\n" +
                "\n" +
                "Trial: The case will go to trial in a court of law, where the prosecution will present evidence and witnesses to prove the abuser's guilt.\n" +
                "\n" +
                "Verdict: The judge will reach a verdict based on the evidence presented at trial.\n" +
                "\n" +
                "Sentencing: If the abuser is found guilty, they will be sentenced according to the Domestic Violence (Prevention and Protection) Act of 2010, which may include imprisonment or a fine.\n" +
                "\n" +
                "It's important to note that fighting domestic violence in court can be a difficult and emotionally challenging process, and it's always advisable to seek legal advice and assistance to ensure that your rights and interests are protected. Additionally, there are several organizations in Bangladesh that provide support and assistance to victims of domestic violence, and you may want to consider seeking their help as well.\n");
        mySection.add(mypParagraph6);


        H2 title7 = new H2("Domestic violence on men\n");
        title7.addClassName("article-view-page-h2");
        mySection.add(title7);
        Paragraph mypParagraph7 = new Paragraph();
        mypParagraph7.addClassName("article-view-page-h2");
        mypParagraph7.setText("In Bangladesh, domestic violence is a criminal offense under the Domestic Violence (Prevention and Protection) Act of 2010. If you are a victim of domestic violence, here are some legal steps you can take to fight it in court:\n" +
                "\n" +
                "File a Complaint: The first step in fighting domestic violence in court is to file a complaint with the police or a magistrate. You will need to provide details of the abuse, including dates, times, and any evidence you may have, such as photographs or medical reports.\n" +
                "\n" +
                "Protection Order: If the court finds that you are at risk of further harm, it may issue a protection order that prohibits the abuser from contacting or coming near you or your family members.\n" +
                "\n" +
                "Evidence Collection: You will need to collect evidence to support your case, such as medical reports, photographs of injuries, and witness statements.\n" +
                "\n" +
                "Legal Representation: It's advisable to seek legal representation from a lawyer who has experience in domestic violence cases.\n" +
                "\n" +
                "Trial: The case will go to trial in a court of law, where the prosecution will present evidence and witnesses to prove the abuser's guilt.\n" +
                "\n" +
                "Verdict: The judge will reach a verdict based on the evidence presented at trial.\n" +
                "\n" +
                "Sentencing: If the abuser is found guilty, they will be sentenced according to the Domestic Violence (Prevention and Protection) Act of 2010, which may include imprisonment or a fine.\n" +
                "\n" +
                "It's important to note that fighting domestic violence in court can be a difficult and emotionally challenging process, and it's always advisable to seek legal advice and assistance to ensure that your rights and interests are protected. Additionally, there are several organizations in Bangladesh that provide support and assistance to victims of domestic violence, and you may want to consider seeking their help as well.\n");
        mySection.add(mypParagraph7);


        H2 title8 = new H2("Robbery case");
        title8.addClassName("article-view-page-h2");
        mySection.add(title8);
        Paragraph mypParagraph8 = new Paragraph();
        mypParagraph8.addClassName("article-view-page-h2");
        mypParagraph8.setText("In Bangladesh, robbery is a criminal offense under the Penal Code of 1860. If you are a victim of robbery or have been accused of robbery, here are the legal procedures involved:\n" +
                "\n" +
                "Reporting: The first step in a robbery case is to report the incident to the police as soon as possible. The police will take your statement, collect evidence, and investigate the crime.\n" +
                "\n" +
                "Investigation: The police will investigate the crime and gather evidence to build a case against the suspect(s).\n" +
                "\n" +
                "Arrest: If the investigation leads to a suspect(s), the police will arrest them and bring them to trial.\n" +
                "\n" +
                "Charge: The suspect(s) will be charged with robbery under the Penal Code and will have the opportunity to plead guilty or not guilty.\n" +
                "\n" +
                "Trial: The case will go to trial in a court of law, where the prosecution will present evidence and witnesses to prove the suspect's guilt beyond a reasonable doubt.\n" +
                "\n" +
                "Verdict: The judge or jury will reach a verdict based on the evidence presented at trial.\n" +
                "Sentencing: If the suspect(s) is found guilty, they will be sentenced according to the Penal Code, which may include imprisonment or a fine.\n" +
                "\n" +
                "It's important to note that the legal process for a robbery case can be complex and time-consuming, and it's always advisable to seek legal advice and assistance to ensure that your rights and interests are protected. Additionally, if you are a victim of robbery, it's important to prioritize your safety and seek medical attention if necessary.\n");
        mySection.add(mypParagraph8);


        H2 title9 = new H2("Money embezzlement");
        title9.addClassName("article-view-page-h2");
        mySection.add(title9);
        Paragraph mypParagraph9 = new Paragraph();
        mypParagraph9.addClassName("article-view-page-h2");
        mypParagraph9.setText("Money embezzlement, also known as theft or misappropriation of funds, is a criminal offense under the Penal Code of Bangladesh. If you have been a victim of money embezzlement or accused of embezzlement, here are the legal procedures involved:\n" +
                "\n" +
                "Reporting: The first step in a money embezzlement case is to report the incident to the police as soon as possible. The police will take your statement, collect evidence, and investigate the crime.\n" +
                "\n" +
                "Investigation: The police will investigate the embezzlement and gather evidence to build a case against the suspect(s).\n" +
                "\n" +
                "Arrest: If the investigation leads to a suspect(s), the police will arrest them and bring them to trial.\n" +
                "\n" +
                "Charge: The suspect(s) will be charged with money embezzlement under the Penal Code and will have the opportunity to plead guilty or not guilty.\n" +
                "\n" +
                "Trial: The case will go to trial in a court of law, where the prosecution will present evidence and witnesses to prove the suspect's guilt beyond a reasonable doubt.\n" +
                "\n" +
                "Verdict: The judge or jury will reach a verdict based on the evidence presented at trial.\n" +
                "\n" +
                "Sentencing: If the suspect(s) is found guilty, they will be sentenced according to the Penal Code, which may include imprisonment or a fine.\n" +
                "\n" +
                "It's important to note that the legal process for a money embezzlement case can be complex and time-consuming, and it's always advisable to seek legal advice and assistance to ensure that your rights and interests are protected. Additionally, it's important to keep records of all financial transactions and to report any suspicious activity immediately.\n");
        mySection.add(mypParagraph9);


        H2 title10 = new H2("Rape case");
        title10.addClassName("article-view-page-h2");
        mySection.add(title10);
        Paragraph mypParagraph10 = new Paragraph();
        mypParagraph10.addClassName("article-view-page-h2");
        mypParagraph10.setText("If you are a victim of rape in Bangladesh or know someone who is, here are the legal procedures involved in fighting a rape case:\n" +
                "\n" +
                "Reporting: The first step is to report the rape to the police as soon as possible. It's important to preserve any evidence, such as clothing or DNA samples, to support your case.\n" +
                "\n" +
                "Medical Examination: A medical examination is necessary to document any physical injuries and to collect forensic evidence. It's important to get the medical examination done as soon as possible after the incident.\n" +
                "\n" +
                "FIR and Investigation: The police will file an FIR (First Information Report) and investigate the case. They will collect evidence, interview witnesses, and build a case against the accused.\n" +
                "\n" +
                "Arrest: If the police have sufficient evidence, they will arrest the accused.\n" +
                "Charge Sheet: The police will submit a charge sheet to the court, outlining the evidence against the accused.\n" +
                "\n" +
                "Trial: The case will go to trial in a court of law, where the prosecution will present evidence and witnesses to prove the accused's guilt beyond a reasonable doubt.\n" +
                "\n" +
                "Verdict: The judge or jury will reach a verdict based on the evidence presented at trial.\n" +
                "Sentencing: If the accused is found guilty, they will be sentenced according to the law, which may include imprisonment or a fine.\n" +
                "\n" +
                "It's important to note that fighting a rape case in court can be a difficult and emotionally challenging process. It's always advisable to seek legal advice and assistance to ensure that your rights and interests are protected. Additionally, there are several organizations in Bangladesh that provide support and assistance to victims of rape, and you may want to consider seeking their help as well.\n");
        mySection.add(mypParagraph10);


        H2 title11 = new H2("Wage theft\n");
        title11.addClassName("article-view-page-h2");
        mySection.add(title11);
        Paragraph mypParagraph11 = new Paragraph();
        mypParagraph11.addClassName("article-view-page-h2");
        mypParagraph11.setText("Wage theft is a criminal offense in Bangladesh, and employees have the right to file a complaint with the appropriate authorities to recover their stolen wages. Here are the legal procedures involved in fighting a wage theft case:\n" +
                "\n" +
                "Keep Accurate Records: As an employee, you should keep accurate records of your hours worked, pay rate, and any other relevant information related to your employment.\n" +
                "\n" +
                "Report the Theft: If you suspect that your employer is committing wage theft, you should report the theft to the Labor Inspectorate or the Ministry of Labor and Employment. You can also file a complaint with the labor court.\n" +
                "\n" +
                "Investigation: The Labor Inspectorate will investigate the complaint and collect evidence to support your case. They may interview witnesses, inspect payroll records, and perform other tasks to determine if wage theft has occurred.\n" +
                "\n" +
                "Mediation: If the Labor Inspectorate determines that wage theft has occurred, they may attempt to mediate a settlement between you and your employer. If mediation fails, the case may proceed to court.\n" +
                "\n" +
                "Court Action: If the case goes to court, the labor court will hear evidence from both parties and reach a decision. If the court finds in your favor, your employer will be required to pay back the stolen wages, as well as any damages or fines imposed by the court.\n" +
                "\n" +
                "It's important to note that fighting a wage theft case in court can be a complex and time-consuming process. It's always advisable to seek legal advice and assistance to ensure that your rights and interests are protected. Additionally, there are several organizations in Bangladesh that provide support and assistance to employees who have experienced wage theft, and you may want to consider seeking their help as well.\n");
        mySection.add(mypParagraph11);


        H2 title12 = new H2("Public sexual harassment\n");
        title12.addClassName("article-view-page-h2");
        mySection.add(title12);
        Paragraph mypParagraph12 = new Paragraph();
        mypParagraph12.addClassName("article-view-page-h2");
        mypParagraph12.setText("In Bangladesh, public sexual harassment is a criminal offense under the Penal Code of 1860. If you have been a victim of public sexual harassment or have witnessed such an incident, here are the legal procedures involved in fighting the case:\n" +
                "\n" +
                "Reporting: The first step is to report the incident to the police as soon as possible. It's important to remember as many details as possible about the incident and any witnesses to support your case.\n" +
                "\n" +
                "FIR and Investigation: The police will file an FIR (First Information Report) and investigate the case. They will collect evidence, interview witnesses, and build a case against the accused.\n" +
                "\n" +
                "Arrest: If the police have sufficient evidence, they will arrest the accused.\n" +
                "Charge Sheet: The police will submit a charge sheet to the court, outlining the evidence against the accused.\n" +
                "\n" +
                "Trial: The case will go to trial in a court of law, where the prosecution will present evidence and witnesses to prove the accused's guilt beyond a reasonable doubt.\n" +
                "\n" +
                "Verdict: The judge or jury will reach a verdict based on the evidence presented at trial.\n" +
                "Sentencing: If the accused is found guilty, they will be sentenced according to the law, which may include imprisonment or a fine.\n" +
                "\n" +
                "It's important to note that fighting a public sexual harassment case in court can be a difficult and emotionally challenging process. It's always advisable to seek legal advice and assistance to ensure that your rights and interests are protected. Additionally, there are several organizations in Bangladesh that provide support and assistance to victims of sexual harassment, and you may want to consider seeking their help as well.\n");
        mySection.add(mypParagraph12);


        H2 title13 = new H2("Copyright infringement\n");
        title13.addClassName("article-view-page-h2");
        mySection.add(title13);
        Paragraph mypParagraph213 = new Paragraph();
        mypParagraph213.addClassName("article-view-page-h2");
        mypParagraph213.setText("In Bangladesh, copyright infringement is a criminal offense under the Copyright Act, 2000. If you are the owner of a copyrighted work and believe that someone has infringed on your rights, here are the legal procedures involved in fighting a copyright case:\n" +
                "\n" +
                "Cease and Desist Notice: The first step is to send a cease and desist notice to the infringer, demanding that they stop using your copyrighted work. This is a formal legal notice that can help resolve the issue without going to court.\n" +
                "\n" +
                "Filing a Complaint: If the infringer does not comply with the cease and desist notice, the next step is to file a complaint with the police or a magistrate court. The complaint should include details of your copyrighted work and evidence of the infringement, such as copies of the infringing work.\n" +
                "\n" +
                "Investigation: The police or court will investigate the complaint and collect evidence to support your case.\n" +
                "\n" +
                "FIR and Arrest: If the police have sufficient evidence, they will file an FIR (First Information Report) and arrest the accused.\n" +
                "\n" +
                "Charge Sheet: The police will submit a charge sheet to the court, outlining the evidence against the accused.\n" +
                "\n" +
                "Trial: The case will go to trial in a court of law, where the prosecution will present evidence and witnesses to prove the accused's guilt beyond a reasonable doubt.\n" +
                "\n" +
                "Verdict: The judge or jury will reach a verdict based on the evidence presented at trial.\n" +
                "Sentencing: If the accused is found guilty, they will be sentenced according to the law, which may include imprisonment or a fine.\n" +
                "\n" +
                "It's important to note that fighting a copyright case in court can be a complex and lengthy process. It's always advisable to seek legal advice and assistance to ensure that your rights and interests are protected. Additionally, there are several organizations in Bangladesh that provide support and assistance to owners of copyrighted works, and you may want to consider seeking their help as well.\n");
        mySection.add(mypParagraph213);


        H2 title14 = new H2("Maid abuse");
        title14.addClassName("article-view-page-h2");
        mySection.add(title14);
        Paragraph mypParagraph14 = new Paragraph();
        mypParagraph14.addClassName("article-view-page-h2");
        mypParagraph14.setText("If you are a victim or witness of maid abuse in Bangladesh, you can take the following legal steps to fight against it:\n" +
                "\n" +
                "Report to the Police: You can file a complaint with the local police station, either in person or by phone. Provide as much detail as possible about the abuse, including the name and address of the perpetrator.\n" +
                "\n" +
                "Obtain a Medical Report: If the maid has sustained any physical injuries, take her to a hospital to obtain a medical report. This will serve as evidence in court.\n" +
                "\n" +
                "Contact a Lawyer: Contact a lawyer who specializes in domestic violence cases. They can advise you on the legal options available to you and help you navigate the legal system.\n" +
                "\n" +
                "File a Case in Court: Your lawyer can file a case on behalf of the maid in a district court. The case will be tried in accordance with the Prevention of Oppression Against Women and Children Act, 2000.\n" +
                "\n" +
                "Attend Court Hearings: Attend all court hearings with your lawyer and any other witnesses to the abuse.\n" +
                "\n" +
                "Collect Evidence: Your lawyer can help you collect evidence to support your case, such as witness statements and medical reports.\n" +
                "\n" +
                "Verdict and Sentencing: If the court finds the perpetrator guilty, they will be sentenced according to the law, which may include imprisonment, a fine, or both.\n" +
                "\n" +
                "It's important to note that fighting a maid abuse case in court can be a complex and emotional process. It's always advisable to seek legal advice and assistance to ensure that your rights and interests are protected. Additionally, there are several organizations in Bangladesh that provide support and assistance to victims of domestic violence, and you may want to consider seeking their help as well.\n");
        mySection.add(mypParagraph14);



        add(mySection);


    }
}


