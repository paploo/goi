--
-- PostgreSQL database dump
--

-- Dumped from database version 14.4 (Debian 14.4-1.pgdg110+1)
-- Dumped by pg_dump version 14.4

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: source; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA source;


ALTER SCHEMA source OWNER TO postgres;

--
-- Name: vocabulary; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA vocabulary;


ALTER SCHEMA vocabulary OWNER TO postgres;

--
-- Name: uuid-ossp; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS "uuid-ossp" WITH SCHEMA public;


--
-- Name: EXTENSION "uuid-ossp"; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION "uuid-ossp" IS 'generate universally unique identifiers (UUIDs)';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: lesson; Type: TABLE; Schema: source; Owner: postgres
--

CREATE TABLE source.lesson (
    code character varying NOT NULL,
    lesson_code character varying NOT NULL,
    source_code character varying NOT NULL,
    label text NOT NULL,
    section_number integer,
    subsection_number integer,
    CONSTRAINT lesson_check CHECK (((code)::text = concat(source_code, '_', lesson_code)))
);


ALTER TABLE source.lesson OWNER TO postgres;

--
-- Name: source; Type: TABLE; Schema: source; Owner: postgres
--

CREATE TABLE source.source (
    code character varying NOT NULL,
    label text NOT NULL
);


ALTER TABLE source.source OWNER TO postgres;

--
-- Name: conjugation_kind; Type: TABLE; Schema: vocabulary; Owner: postgres
--

CREATE TABLE vocabulary.conjugation_kind (
    code character varying NOT NULL,
    label text NOT NULL,
    word_class_code character varying NOT NULL
);


ALTER TABLE vocabulary.conjugation_kind OWNER TO postgres;

--
-- Name: definition; Type: TABLE; Schema: vocabulary; Owner: postgres
--

CREATE TABLE vocabulary.definition (
    id uuid NOT NULL,
    vocabulary_id uuid NOT NULL,
    sort_rank integer DEFAULT 0 NOT NULL,
    value text NOT NULL
);


ALTER TABLE vocabulary.definition OWNER TO postgres;

--
-- Name: linkages; Type: TABLE; Schema: vocabulary; Owner: postgres
--

CREATE TABLE vocabulary.linkages (
    vocabulary_id uuid NOT NULL,
    preferred_definition_id uuid NOT NULL,
    preferred_spelling_id uuid NOT NULL,
    phonetic_spelling_id uuid NOT NULL,
    alt_phonetic_spelling_id uuid,
    kanji_spelling_id uuid
);


ALTER TABLE vocabulary.linkages OWNER TO postgres;

--
-- Name: reference; Type: TABLE; Schema: vocabulary; Owner: postgres
--

CREATE TABLE vocabulary.reference (
    vocabulary_id uuid NOT NULL,
    lesson_code character varying NOT NULL
);


ALTER TABLE vocabulary.reference OWNER TO postgres;

--
-- Name: spelling; Type: TABLE; Schema: vocabulary; Owner: postgres
--

CREATE TABLE vocabulary.spelling (
    id uuid NOT NULL,
    vocabulary_id uuid NOT NULL,
    spelling_kind_code character varying NOT NULL,
    value text NOT NULL
);


ALTER TABLE vocabulary.spelling OWNER TO postgres;

--
-- Name: spelling_kind; Type: TABLE; Schema: vocabulary; Owner: postgres
--

CREATE TABLE vocabulary.spelling_kind (
    code character varying NOT NULL,
    label text NOT NULL
);


ALTER TABLE vocabulary.spelling_kind OWNER TO postgres;

--
-- Name: vocabulary; Type: TABLE; Schema: vocabulary; Owner: postgres
--

CREATE TABLE vocabulary.vocabulary (
    id uuid NOT NULL,
    word_class_code character varying NOT NULL,
    conjugation_kind_code character varying,
    jlpt_level integer,
    row_num integer,
    tags character varying[] DEFAULT '{}'::character varying[] NOT NULL,
    date_added date
);


ALTER TABLE vocabulary.vocabulary OWNER TO postgres;

--
-- Name: word_class; Type: TABLE; Schema: vocabulary; Owner: postgres
--

CREATE TABLE vocabulary.word_class (
    code character varying NOT NULL,
    label text NOT NULL
);


ALTER TABLE vocabulary.word_class OWNER TO postgres;

--
-- Data for Name: lesson; Type: TABLE DATA; Schema: source; Owner: postgres
--

COPY source.lesson (code, lesson_code, source_code, label, section_number, subsection_number) FROM stdin;
GENKI3_L00	L00	GENKI3	Greetings and Numbers	0	\N
GENKI3_L01	L01	GENKI3	New Friends	1	\N
GENKI3_L02	L02	GENKI3	Shopping	2	\N
GENKI3_L03	L03	GENKI3	Making a Date	3	\N
GENKI3_L04	L04	GENKI3	The First Date	4	\N
GENKI3_L05	L05	GENKI3	A Trip to Okinawa	4	\N
GENKI3_L06	L06	GENKI3	A Day in Robert's Life	4	\N
GENKI3_L07	L07	GENKI3	Family Picture	4	\N
GENKI3_L08	L08	GENKI3	Barbecue	4	\N
GENKI3_L09	L09	GENKI3	Kabuki	4	\N
GENKI3_L10	L10	GENKI3	Winter Vacation Plans	4	\N
GENKI3_L11	L11	GENKI3	After the Vacation	4	\N
GENKI3_L12	L12	GENKI3	Feeling Ill	4	\N
DUO_01_01	01_01	DUO	Hiragana 1	1	1
DUO_01_02	01_02	DUO	Hiragana 2	1	2
DUO_01_03	01_03	DUO	Hiragana 3	1	3
DUO_01_04	01_04	DUO	Hiragana 4	1	4
DUO_01_05	01_05	DUO	Greetings	1	5
DUO_01_06	01_06	DUO	Katakana 1	1	6
DUO_01_07	01_07	DUO	Intro 1	1	7
DUO_01_08	01_08	DUO	Katakana 2	1	8
DUO_01_09	01_09	DUO	Intro 2	1	9
DUO_01_10	01_10	DUO	Katakana 3	1	10
DUO_02_01	02_01	DUO	Food 1	2	1
DUO_02_02	02_02	DUO	Time	2	2
DUO_02_03	02_03	DUO	Routines	2	3
DUO_02_04	02_04	DUO	Home 1	2	4
DUO_02_05	02_05	DUO	Intro 3	2	5
DUO_02_06	02_06	DUO	Counting	2	6
DUO_02_07	02_07	DUO	Family 1	2	7
DUO_02_08	02_08	DUO	Restaurant	2	8
DUO_02_09	02_09	DUO	Activity 1	2	9
DUO_02_10	02_10	DUO	Position	2	10
DUO_02_11	02_11	DUO	Vacation 1	2	11
DUO_02_12	02_12	DUO	Hobby 1	2	12
DUO_02_13	02_13	DUO	Family 2	2	13
DUO_02_14	02_14	DUO	Transit 1	2	14
DUO_02_15	02_15	DUO	Clothes 1	2	15
DUO_02_16	02_16	DUO	Hobby 2	2	16
DUO_02_17	02_17	DUO	Weather 1	2	17
DUO_02_18	02_18	DUO	Food 2	2	18
DUO_02_19	02_19	DUO	Direct. 1	2	19
DUO_02_20	02_20	DUO	Food 3	2	20
DUO_02_21	02_21	DUO	Dates	2	21
DUO_02_22	02_22	DUO	Shopping 1	2	22
DUO_02_23	02_23	DUO	People 1	2	23
DUO_02_24	02_24	DUO	Activity 2	2	24
DUO_02_25	02_25	DUO	Nature 1	2	25
DUO_02_26	02_26	DUO	Classroom	2	26
LULI_JP_L03	L03	LULI_JP	Basics	3	\N
LULI_JP_L04	L04	LULI_JP	Verbs	4	\N
LULI_JP_L05	L05	LULI_JP	Questions	5	\N
LULI_JP_L06	L06	LULI_JP	Adjectives	6	\N
LULI_KANJI_L01	L01	LULI_KANJI	JLPT N5	1	\N
LULI_KANJI_L02	L02	LULI_KANJI	JLPT N4	2	\N
LULI_KANJI_L03	L03	LULI_KANJI	JLPT N3	3	\N
LULI_KANJI_L04	L04	LULI_KANJI	JLPT N2	4	\N
LULI_KANJI_L05	L05	LULI_KANJI	JLPT N1	5	\N
\.


--
-- Data for Name: source; Type: TABLE DATA; Schema: source; Owner: postgres
--

COPY source.source (code, label) FROM stdin;
GENKI3	Genki, 3rd Ed
DUO	Duolingo
LULI_JP	Lulilanguages Japanese!
LULI_KANJI	Lulilanguages Kanji
\.


--
-- Data for Name: conjugation_kind; Type: TABLE DATA; Schema: vocabulary; Owner: postgres
--

COPY vocabulary.conjugation_kind (code, label, word_class_code) FROM stdin;
IRREGULAR_VERB	Irregular Verb	VERB
GODAN_VERB	Godan Verb / U-Verb	VERB
ICHIDAN_VERB	Ichidan Verb / Ru-Verb	VERB
IRREGULAR_ADJECTIVE	Irregular Adjective	ADJECTIVE
I_ADJECTIVE	I-Adjective	ADJECTIVE
NA_ADJECTIVE	Na-Adjective	ADJECTIVE
\.


--
-- Data for Name: definition; Type: TABLE DATA; Schema: vocabulary; Owner: postgres
--

COPY vocabulary.definition (id, vocabulary_id, sort_rank, value) FROM stdin;
70ac6430-b36b-5879-9b65-bff42d16d560	f552ea3a-8b7f-5433-aa20-4c93a6868fb3	0	Good morning
f67c3c1b-0feb-51e4-b50e-c580aa54df4e	8359d7d1-d6d6-5995-962e-3f6566232911	0	Good morning (polite)
ff0f69c5-fdad-56c5-aa06-a598b8738d2d	4c55fd59-1160-52d2-8383-00f292668c1f	0	Good afternoon
aa9ebe48-87ca-5d72-9fae-6ab3d3cb5a41	8d46e404-38ff-518c-a25d-cb95d6f2c511	0	Good evening
b00a7fa2-3a44-55d5-b4b6-55bb0d615cd7	1184380a-27db-5f8e-841d-7b105c2a7366	0	Goodbye
753a8f8c-a381-5612-9a2c-5f0b1375dca9	3ecfb07b-0a66-555c-97c4-5af7cb806507	0	Goodnight
32bd2dc7-78a3-574f-a1b9-f1806e6d89b0	a419bb62-f061-5ac6-856b-6f85002642c6	0	Goodnight
ee0d82ff-fb58-505d-8b3f-8470835f55bf	c7a645f7-256a-536a-a5ee-5f85e8ce1137	0	Thank you
7b689a5a-2866-5afc-98ca-0bdaaf095f54	b626308d-6418-5a2c-9151-7a8a368f27a3	0	Thank you (polite)
48f53540-7686-5ab3-8de4-6384fa259cbf	1913dbc6-4712-549a-9274-dc3511cecbfe	0	I'll go and come back
88041d5e-1d65-5fc8-b03c-f715ba4ea0df	e7161fcf-fbaf-5971-8adb-70745ae24103	0	Please go and come back
d55290c9-3c73-59ae-9616-c973808fc680	c2a4dbcf-dba1-5e27-964f-5e2f7b358725	0	I'm home
11614ea6-fab1-5e3d-b72c-779848bb02b6	b06abb09-c9bd-5beb-a7b7-d184bedf72ca	0	Welcome home
c199c713-3494-508d-afa0-4cdb9281d245	47b08527-885b-50bb-b538-180dc308199a	0	Welcome home
1001d407-e258-5e91-9ff1-0109995e9373	797b03e3-1495-52c0-baf4-ffb9cb324089	0	Thank you for the meal (before)
31bcbff4-fb38-56e5-a7b9-08120bf11acf	59bad243-8b31-54f3-9d7b-6de0961bc059	0	Thank you for the meal (after)
7719c179-8686-5442-87cc-1e6e95c174ef	05436324-1858-5a2f-a2a1-a41d00f862cc	0	Thank you for the meal (after)
15e0d3c4-823b-5f7c-8d2f-1815335f67dd	136d6d6d-0487-5709-ac27-2c376bfebd43	0	How do you do?
ee0ed371-fd24-5411-b704-5af7b247db99	ddd5fd1f-f041-576c-a479-d8d9a3221370	0	Nice to meet you.
1740fbc3-92a0-57dc-bf04-7670587710b0	d43cc787-37af-5dd1-92b5-fc756f18298a	0	college; university
ed658793-513c-538a-b413-76e1565f1c47	3c890a54-15f9-56e1-9bd5-3c91274b6246	0	high school
abd9699b-4a0b-584f-9aa9-34f5ad747446	6de86986-05ed-58da-a055-9d49c89f7a06	0	student
81b959fa-48ef-5c9c-ab21-fd42ba234894	d1cb9e5b-6cc5-5339-b33d-4e948229156e	0	college student; university student
7213f37f-0e8c-5ca6-833c-b27ee386b797	efe5fea4-0476-5328-bd18-2a469a8b6c3e	0	international student; foreign exchange student
eceee346-c010-5e88-9b4c-f5ab85a02acf	c21b42a5-1ef1-51cf-9402-199d2b562a47	0	teacher; professor
9cf13027-c124-56c6-b31f-8651d4bd244b	057a7cd1-f1a6-50e2-8449-3b6f04c67305	0	...year student
3a782f59-e890-5100-8892-36908ee3bcc8	e71aac30-c949-5b47-b118-d02a5d703701	0	major
289c8d20-3dae-52b3-83d5-fcedc55ec6ce	4b1d8b7f-6477-5881-9439-1a7f30b26c9d	0	I
9d503e44-704a-5102-bf2f-e3dd0dc3f79e	f65b93fb-1086-5405-8163-394db3e83014	0	friend
65c20de6-3266-50cc-8687-4a015bc6764b	c26e8356-f73a-5ddf-b045-432580c37468	0	Mr./Ms.
98cc142d-1e63-5134-941a-4ac349a6c9de	0ef6972f-5e7d-5b51-be7f-8f39a5fd5e0e	0	...people
675a7b2d-a38b-5f86-b3a8-3a8753a57133	146b7705-d9cd-511f-988c-5b2cc7661b75	0	of course
c2a55006-9ec2-59a5-99bc-99385a898aab	8bedfb60-1599-58b0-9568-5e72b8cf6b7e	0	office
d929ff1d-1d63-5290-9ed7-86913a847aa1	25f93b07-dc04-553a-8794-329cc907dbe5	0	school
dc7e50df-0ba6-59b1-b00e-b596fef35437	37eba762-70cf-5c5c-b689-621f26c1ee5f	0	elementary school
b2576dd7-bc99-5998-8b21-99298c003eff	99cdf9f9-df8c-5d7a-9ea1-a7c003c9f289	0	middle school
242c7787-c6a1-5cba-85b9-71d7f0098e85	947e6f87-e4fe-5b27-9bd3-215e8b1fc71a	0	foreigner
066fd172-a3e1-5ee2-b33d-824ab48c91e5	dd191fac-e29b-5262-b2a6-f9f2875a4a3f	0	to live; to reside; to hinhabit
f0725114-391a-571d-a983-1dee80598a28	fc82f2ac-3b17-582f-9259-7200e1430824	0	Kyoto
e9f50173-81af-532a-b840-e9ad03c23231	15c5fee6-dda7-5d40-b7fd-382d97a28b9c	0	Osaka
3c9671c0-0b3d-5fc6-9456-69c968da374d	39458010-a5eb-5b13-b054-4cc458702c56	0	Tokyo
d8a0901e-ad81-54e8-886d-1a7c5bdf1281	091d6d9c-5d19-51f5-bc28-3b5281042464	0	to say; to be called; name
9a2bb1cc-bd0c-5df7-ba66-9039b769b9f2	6e44df4a-4366-5c1a-86af-87a4e5f61fc6	0	That is not right
8af6616e-3d26-5b2c-9e16-d3cf99ff4340	7748dd60-2126-5e30-a5b6-39596a0e25e4	0	What is the difference?
7a1f4917-96ab-5ebe-adb4-89b96fe27664	dce3fe6a-497c-54b2-a121-df6767084258	0	Now
a0f9d2e3-9d86-57a2-93e8-9377bcc14f29	42ffdbae-c27d-53a4-b113-95583953e16c	0	A.M.
720c7553-f414-50c1-bf89-61b4b9192117	94611381-0488-58aa-8cf2-12ad9ff4e2dd	0	P.M.
6ff0b4a8-8a4b-5875-8934-99edbd739a9c	40502e18-b4ba-50b4-89b7-23fd3b4d4a21	0	...o'clock
9baf2625-b152-5c51-b81c-9fa26799fb6a	5372b023-b803-551c-9a7f-f404804f63f1	0	half; half-past
33383647-612f-5d18-b723-7c531eca7c85	2cf1d32f-b261-5ba6-829c-a9f1237a88d6	0	...minutes
53a4bf51-dd4b-53f0-bed0-991cd6c6c747	a39e794c-9682-5cd5-9c5e-dd961aa92bc5	0	approximately
a88eb384-641a-506e-8a3b-ab52828c4057	9e67a677-9058-536e-85d6-389ea5138c86	0	exactly
eaebfa66-8524-5e12-98ac-e36bf99b3d2b	252852aa-aefd-504b-b671-668a33020492	0	zero
75badafb-8301-527d-8fdd-91321a9cc2fb	cc6a4913-6f39-521b-a0cf-9631f640800e	0	Japan
38eb464c-6609-590f-88d1-bb1f634dd591	522e0796-297d-5bf2-be85-e93596baa6bf	0	America
4972f7a4-6d55-5486-aebb-1bbb2c8c68ea	a420a37b-593e-572e-aae7-d724eca6803d	0	...language
6121bd57-f362-559e-9bc1-cfcbb75a14fe	c59169cb-e5ad-5311-a9ce-43764a5ddda6	0	...years old
a42680cd-c376-5291-a8b5-d777ad4e44d9	1731f988-dcfb-5b31-bda9-c8988a5711bf	0	telephone
a2178e5a-c9dd-5dba-aee0-adfeb6e59141	f6524cfb-fa66-56ae-ae4c-0e38a8b6e54c	0	number (in a series)
98122d83-a56f-586d-b3ba-6126b5879628	d8f98686-282e-5c5f-88b2-4e046e707e54	0	number
5d6b95fc-4aa6-5805-93f3-f3a36b6e11c7	728f5a87-3848-5450-a941-f9cd0f898067	0	name
6940df74-f3c9-5f8b-91a1-581ae175d8fc	507092ff-6dc3-5749-a9c3-79ba8d2bc360	0	what
b1d437ff-bad4-5d94-972a-6315eb3e545f	84e857b9-5605-51fb-bfb3-8cc08eb579d3	0	um...
98aeb9e6-1da7-51fd-879e-13a54f1538b4	dde2be42-c934-53fc-9dc6-3ce12b727924	0	yes
620810c9-2712-549c-af3d-1ac92a8ff0c1	0428342a-6b57-5e6f-b1c6-4aa6ccfdb267	0	no
046e8f71-d4c3-5d2f-b2ee-30abd28bc9f6	a48cfff2-551b-5525-bf29-f706571e7e15	0	that is so;  that is right
c97665b5-9732-5584-82a8-9406fe17cd64	311cf3c0-5798-551b-a933-eb33c4ce7843	0	i see; is that so?
009be582-f140-5869-8984-eccbc918a36f	40483fe8-7fda-56b7-b28b-eedb9da50a52	0	England
6b95c105-bdc0-5cba-a630-095e120bcdb3	2a0ff39c-471b-56d1-a2b2-39f20596f70c	0	Australia
95234b6b-a89b-5231-b9a5-4f35c4419035	5cb08712-5c04-56f0-aaf6-a8e76a1e0ef4	0	Korea
8a4b908a-0b93-5c53-840f-cde8e5f4df5e	b4a146e5-f483-50d5-8b18-ca71fdb3f570	0	Canada
35eab8ff-4a4c-56e4-9acb-8bf93f633927	54ca6cf2-c9f0-5a77-b53c-8e4a2fbe76ac	0	China
4f78cc1a-6d5c-53be-a8de-22ba07ac8eac	f3f3ba33-de3b-5fa1-9b99-eee62ae2e024	0	India
e9a4005c-2a81-5874-9f60-be17094fb1a8	eb20360e-5c1a-58b8-9bf8-040795c5bf0a	0	Egypt
f70590ac-0c45-5376-8012-fdf0e8088d66	ae144a7c-293f-5f24-81ee-37d5594d6aed	0	Philipines
b6ad77c1-fcbb-5b1d-b163-5783ed1fbc28	abd1e50d-f284-5a21-831f-344cb338414e	0	Asian Studies
3280bf28-482e-5a77-93c1-59e5544438a6	13912d04-d947-5c72-a192-740c19a28c86	0	economics
0cac7dd2-6b30-51a3-be00-9d85db1527a8	7672ef18-d91f-548f-a938-970d6a741586	0	engineering
199f07f7-392c-5a2b-be9e-9a719a7df0b0	471eeab4-7852-53f9-ba6c-342f13746958	0	international relations
8e9daa8d-f29e-5a32-972c-b2b13836ed7e	9e62feab-62dc-5faf-92d9-8cd423af7dab	0	computer; computer science
97dbf2ef-f2b0-5619-a13c-aae71b57a930	15b8fb86-6025-524c-a968-0d8e442c0d8e	0	politics; government
19c76a67-ef88-50a7-8bf8-7bdf4848ed8f	ee82d23f-991e-59ba-9646-d407d35b46ae	0	biology
7c25111c-2a30-5205-a5db-a539ab6c798d	a74f0164-da3d-59dc-bbd0-39cf065ee813	0	business
d04fad4f-88a6-560e-b256-26700f659d4b	f41b3958-7c0c-5c05-9ea8-d0099b014f0c	0	literature
0e9f0eec-41ce-57da-bbf4-62d7070cbfaf	0d91d253-e394-572c-a6c4-37e20ed0f603	0	history
ccdc24c5-50d4-5ad9-9623-53232651beec	a9d318d5-97e8-5990-916c-fd300d951e2c	0	physics
55ad419b-1ed6-5857-997a-370565e9f4bc	45983eaf-745d-5a47-bd34-aa09444736a4	0	doctor
9933d252-41f8-5aeb-a630-abd529aead58	b01b2de0-f80a-587a-99bb-8e7e2157feec	0	office worker
4850dc97-dea9-508f-a8db-e078f2de63e2	a2bb0866-9ce3-5b9a-bc60-49b48a40e40f	0	nurse
c9aefe9f-c036-59ca-9274-44470cfc96a3	c511ec54-4f00-5426-89ef-ee1558ccfb60	0	high school student
98b29dec-0594-5261-9b67-bb1a55744a5b	7d3f6d4b-fd98-5a19-ba56-b7c707092305	0	housewife
87b4cacb-b739-5dee-afdb-b869bbed05d8	98e48ea2-8566-55a1-bfb8-d5aaf1a060d0	0	graduate student
1674f170-0195-5415-8f5f-cd97b6fd1cf3	902b7ccb-091c-502b-a413-5a3a38c655a4	0	lawyer
35f50c3a-d8dc-55d4-9ee8-65b49a90224d	f8d03abe-255a-5717-811a-91f511c39759	0	mother
d36f1c25-62a1-5648-bd49-083bc58ee7f8	d7a063c8-998c-50a3-825c-86f896a5a0c7	0	father
e13f5421-e03c-56df-8fae-fc7176d91261	e09e6607-0754-53e7-ac90-7d5eec6a3442	0	older sister
dca971b5-63ee-5942-8df6-44ecc006d6e7	942ce296-4fdd-5a7d-9c2c-53d4c2ae2de7	0	older brother
368114ad-1c0f-5925-bcee-ff97cecd6343	d4387364-3f1c-51be-9762-ddf41f844fb7	0	younger sister
a086d90c-90c8-5a73-b5b7-65821a44bc94	acc5e753-294b-571f-9ee7-a7cd6eeea235	0	younger brother
dd943cf5-3d3c-5f13-a3cd-6c51867bada9	58ecba76-692b-5baf-a096-62bf3e752da2	0	this one
b3b9b81d-b2af-5306-97f1-1e2ef5db0333	8373ee97-fb02-5fa4-9f3a-e6d0bfe42042	0	that one
2fd8013f-de54-552b-bbde-6fa0c7f43454	e3286acb-a31b-5fb6-9c16-28ac16fcc269	0	that one over there
e674bc96-2f20-53a1-aa5e-aa527d21e056	9ef8e062-db86-5059-82be-ccc85580d5e7	0	which one
14b5fe84-4752-5db6-b3e6-21c9891951d8	430b4ea9-6092-52c7-b2bf-4300de6196c7	0	this...
7044c077-2e58-5141-be32-5f7aadcecbce	9d9f54be-2a19-5147-8935-e7c3c887fa03	0	that...
4b1b31e1-528c-5c99-8fcb-22eae553d2b5	908efe1a-b6e9-55d5-b550-461cea175af4	0	that... over there
3c0f2f7d-d5e5-5369-93c8-50129680fdf4	a9b7cffc-2d14-58ae-818b-3ac3b6e6e4d0	0	which....
16022cbb-94a2-53e7-9067-9682e1e53b85	9c591bfa-c4a7-5263-b2b1-7c8d14e10c34	0	here
3a496e65-9d8e-571f-88ef-a562e79495ef	0279527b-c398-5856-aad3-11b834907f56	0	there
08023337-313e-56e1-a77d-44ad5b3f2d8b	5587745e-e524-5159-8fe6-1e8637b90455	0	over there
c1731080-65e8-5a77-bad7-5bd296dff087	1502a2cb-059c-5a9f-baab-af68a0d09ac8	0	where
393c8163-bc02-577c-a142-2f53a1f0486d	93eaf294-a292-5980-b17e-de43755335c9	0	who
54364495-2043-5b80-a659-7ec91944c8f9	f5fce96d-2982-5d7a-b7ce-9dc8914c6ab3	0	delicious
06d21d03-043f-5c07-a95f-1079c2b3aae3	10a5183d-9b5d-58c7-885f-0210e56c1dca	0	delicious! (slang)
f201b28f-1b00-54f3-b0cd-98a42d68c776	f7aa023d-a413-562b-bf45-75367462a4b2	0	fish
ba766475-bbe4-595f-9268-b4a791a41404	81b8f353-cd1c-53e4-a0f4-1630c490c959	0	pork cutlet
2e8b8244-4040-578f-9259-c08f47114166	e2c0e2f3-891a-55e3-ad28-285669d27287	0	pork bone (e.g. ramen broth)
bcc9b737-1efb-5df9-99bc-6ac1e188ecf2	2dd4acb2-11ca-50c5-b365-c22616ea316d	0	meat
70aff8d7-e756-5b41-bcf8-e0979ff802c3	3ab19c55-4c6a-53e0-afce-41968363d92a	0	menu
a6cef35b-53cd-5aff-bb87-a147a3cf35fa	496222cc-1cb3-50c8-9e7e-bbc7d327b78c	0	vegetables
bfd18a6a-40c7-5f0b-8307-30a763e0794f	4fbc0a01-e32a-5449-8efe-3ae37aee4f2f	0	rice
e43fe162-0590-5f49-949c-dd364151e3db	b74ca7fa-4622-5a52-b9df-5260878e687b	0	bread
b2abf7cc-ffcd-5c82-afac-83ba81b65921	5e2b2d0d-7461-56e2-a151-9a10375d5615	0	egg
0ab63e1b-e014-5c3e-865c-3debeab8f07c	12c11165-7996-5b13-bf19-c3afc419afb2	0	apple
f01ee2b6-5f41-5f92-bd38-432d8ec72cc2	b1852be8-7a0e-5541-b94d-29be625cb423	0	umbrella
1d943538-3c44-5acc-9534-33e5e8ff737b	b5d661b7-f275-5647-a86b-cf4bd963f512	0	bag
7af83019-171e-5618-ae0d-c2feb1fa0f92	96fb26f0-20df-51e0-b1c4-793ab736c2d9	0	shoes
ddfd0720-cf03-5c8b-9eb4-a3aeb1ce9ff1	b802b5a4-5804-5f5a-a193-e475bceaf5dd	0	wallet
5880f681-5fce-5271-8fdf-ddff3d293edb	bf5e0762-effe-5d38-8786-503cde98e504	0	jeans
7048d6ad-bcab-59ec-ad20-b822ed218420	04bddd51-0749-5efe-9946-6d326f61bcb6	0	bicycle
62a33245-0317-54bc-955b-fd9e155042bc	f8a3e40d-f42e-5ba2-995d-3abc33e63a41	0	newspaper
4ef95b9f-604d-5bdf-b02a-3415a8b56fa4	e7323b54-9fa5-5cb7-8d68-d2ebe9b379ea	0	smart phone
1a0e03b4-8b99-53a2-92bc-202ac3662891	1a0585e5-3bd0-5903-90cc-d3381778da68	0	t-shirt
8abe6eb7-564e-5d16-b7fc-637ab4ce4679	1c5e45ac-cc4a-5d53-b566-d6d832828ba0	0	clock
09caa815-f519-5288-bab2-8c686e70209d	612eac67-3ec3-535e-94a3-249d60f51046	0	notebook
2bf8e240-348c-577d-8379-0e9aaedfd052	442d1c8e-a848-5fa4-8fc9-d398441b4b65	0	pen
7ccdef60-7734-57c3-bc38-b7b144b90ab0	5f6cc5c3-6625-5caa-9277-f43493360fc2	0	hat; cap
2951c376-3600-57e2-8ede-fe37173e420e	6d055f49-622a-5d8a-a7d2-11de3279b9ce	0	book
1b01ab3a-2e1c-5ef7-93fe-e4e18addc4a2	3a52dad1-de68-523b-b6ad-3148496bcf8e	0	bank
5ba48d55-a102-5aaa-9c22-c148423244c5	6c108fe0-b52d-56de-96f2-f8e0bf450367	0	convenience store
8fadeb9d-8ac2-52b7-ae99-9ebf12c82f03	14edc8de-8ed3-58f5-a083-13a34db6f490	0	toilet; restroom
dff1996d-452e-530d-af3e-36186de7ac75	dd52de46-6d78-5bc3-96ab-b25e9be9e291	0	libaary
096c68b1-06f4-5f60-b323-c1803c3e8633	89b553be-905d-57aa-946c-86dc9f366aa2	0	post office
9c4d029b-134c-5412-b8c4-9ae1c5a0c032	f7d4fade-03e1-53e3-94c0-24034e6a9e08	0	how much
b5600c12-3aa8-567c-938b-e7a8d407779e	fbae6594-96c7-53f0-a80b-bd325d5a22ff	0	...yen
d5cc3edc-2d1f-5407-9971-8e8689185f45	dcb08e4f-eb7c-5536-bd57-ee51127a14be	0	expensive; high
15e296e3-94fb-52f3-b3a9-9ee9eb2bb6a0	1cee44df-fc33-5279-8d09-5051e714782f	0	welcome
c2cc6878-a61c-521f-ae40-73dee64c8640	845d24c6-6c7c-50f4-8dd7-450485839fa5	0	...please
be7c4dac-9506-5253-9d4d-014761f015b0	fc6be215-8cf3-55b8-8a6b-0dd345852963	0	please give me...
dc8c73bb-3ef7-5159-97ca-e0780fba9926	3bb29baa-6e40-5c58-93cd-2000ea1120eb	0	then...; if that is the case...
b225b075-4310-5d12-93dc-d85956e285da	8dc54b41-91cb-5289-bb0b-5f4a5854fea7	0	here it is; please go ahead; i beg of you
81cd1a87-2d8b-5439-8a97-0c236c2753b5	5891a34d-ba0a-5458-b42e-f82c60d27f9f	0	thanks (casual)
afe65193-cf68-561b-a5cb-e35b7b496b31	48b8587c-d363-5b54-94d9-c9e5dfff2412	0	zero
25f88057-2ae2-5cfb-976e-8bf7a89344fd	c007b505-323f-5517-8353-ed093d1571de	0	one
4ee83cd9-765e-598b-9b23-a553a49ccc62	8873834d-7358-505d-a93b-7782dbd00024	0	two
ccccdb55-dd94-5e0b-84b3-e5f9aea3b4c3	0f9e7c25-4cfa-5186-a893-0f59b7e34745	0	three
7ce6f95a-1009-5be0-b599-de6fa2f9347e	59d58d35-f0f2-5535-8404-8cb786e2c830	0	four
4557611d-face-5211-8356-f37f26dfd718	82339058-2123-58a8-93dc-b1c0b05cdae8	0	five
e5e5ace6-768d-564f-bcde-087269bfe38e	e469a7d8-cfad-5a85-ba5c-cb1d0c8ca1b0	0	six
a3b4604c-0b6a-59b4-b582-0e5c8e962f10	49ab0509-4a86-552d-975b-fc49e74790d4	0	seven
66ccddbc-7a6b-5b27-bb84-904067404aff	6d9b3812-644e-5890-94cc-09a2fd01a30a	0	eight
4dafde42-f51c-5bd6-be5c-31ff34b3d171	aaf16bcd-1ecc-5f72-97a6-497825719aba	0	nine
7477317f-05dd-52d1-bf47-9a9a6ae21944	e05ed6a4-edcb-5052-9760-1e10c70c2236	0	ten
52448f2c-43b4-5bef-8950-4fc15921eea2	0f06fb63-c508-57a2-8983-2cb9f5dfd2e7	0	hundred
99cfad43-cd82-5fd6-b14d-4937691bc6a7	662aaf34-2fd3-5ab7-ad40-ee6e4862fc2c	0	thousand
546365b8-30f9-5ac8-9615-3f2f497fec2b	06eedaf7-2c40-54e4-a1ea-669b265fda8d	0	ten-thousand
ada1d499-ecea-51fc-b8a9-b365e7e312ca	b82fa253-fbb7-559c-b127-77d2262df800	0	movie
0da32afd-48f5-5436-a3e0-1b8b54e265bc	aa05a718-c2a5-5083-9c3d-b0e4d1bd09a8	0	music
d1713f19-aaaa-56f7-852a-3978de3b793b	3bef2c81-886b-5bbd-9f1b-dfc94288bea2	0	magazine
ef6e20f2-87ec-523c-94e3-f9fe3fadd8dd	bbf5a302-a23c-5142-b721-2aa90ff1e8dc	0	sports
927f9ade-e660-59b6-b454-7837ea418f62	738002f3-36da-5668-8a8b-46415de8cd52	0	date (romantic)
2bb8e10b-3a10-5ae7-a8ab-697917d98563	8f790b97-9843-5efe-b338-e1c37618c616	0	tennis
e0f8777a-8c58-5cf0-9ad9-dfa2fdc145f2	5ca8a07b-ac34-5789-ab4a-8bd29d51929c	0	TV
20908f2f-a090-56d2-801a-fbedbea38f16	b3e732b3-61c4-574d-8901-b1c74076b5ea	0	ice cream
9d3a9b01-8f1b-5d20-a97c-6549adc84042	720a22a5-3132-56e6-8380-03a20e2a4af1	0	hamburger
f20ebb6e-5253-5ae1-8d7d-30f9c7fff09f	dbf81e7a-c846-5129-b017-a31ad90eb752	0	sake; alcohol
d577df4d-e545-5f7e-8ac8-691779895c6d	4b6153f0-1c2e-5330-94dc-e71afa17d386	0	green tea
5e3e2e75-7e8b-555f-99b7-64b49b06c936	c675acf7-4ab1-5a0a-beb3-e65aff83932a	0	coffee
6a88fee3-bbb5-5ef6-a79c-b28c0e7d6be8	ff69f94e-49fa-5015-a8e8-6bdab429f087	0	water
5a5ef3d4-97d0-559d-99a6-87fd6a29035a	455c3d21-e4c5-5cae-a4b1-2f4a0b988e50	0	breakfast
6bd06588-251f-581a-ac11-057711cfe513	43903683-a322-530c-88e1-a1c406296be6	0	lunch
3c93761c-c9d9-5ea5-abb1-2fc4ff69a450	4e1fc6f4-772d-5a1f-8391-5453bcbf4817	0	dinner
063882d2-f6a4-5927-8d33-7fe0a94a6f1b	e6aa13b6-6f6a-57db-8433-701ba55fa918	0	home; house
595879f4-7bff-5ffc-8650-ae37b7f89bd9	522046f3-2cfa-565e-a8d5-15f048b49776	0	my place
e04ba1c1-ec1d-5b71-8707-00b2d9639ccc	c5ebd7d9-06b2-596e-96f5-7366810ea78c	0	cafe
14fe3bca-81a8-598d-ae3b-bd2068ea6081	3972d239-6e9a-5163-bf43-c4a2b81783db	0	tomorrow
d0fc0984-fb21-5b79-a0c6-4b404a1c4cb6	a4dbf3ac-a372-58a7-b9ac-a7f018ba587c	0	today
877384c3-9344-5833-9646-ab43ec6e4511	b415c8ff-d8fb-5e40-9a89-ed5fa1997831	0	morning
63e28731-0166-5521-bc93-e8139e4e34d7	e872c853-3159-56b7-834e-066e364a5782	0	tonight
f78152c6-9972-5880-8932-4b8e2e0fa661	7ec8469c-ee0f-5a33-9f9f-e025e6cffdd0	0	every day
c81519c0-98d0-575e-95ec-dbeb16a1e096	1efcd811-18f8-5b64-be29-9f46fbef3286	0	every morning
5486a985-4f94-5b7c-abf8-3b50824d4832	9e1ccd85-e76b-559b-9c13-ac2202a04849	0	every night
930211dc-15e1-571e-98ba-fc458ea76bbc	788e64aa-46ee-574d-aad9-83c8b56bc40a	0	weekend
7396ce96-d427-5d3b-9aee-bdea76136921	f7d20fe3-57b3-5e6d-85f6-5b54b0ba7088	0	Saturday
4e1a4700-9373-564b-ad1f-d8f771d2e856	61637bb4-cc4a-52be-86fa-7fb678ab6e62	0	Sunday
f816ed1f-c8bf-5d48-909f-76f27d406879	f05e2adf-83c4-5ccf-a74a-fbd5664dceb1	0	when
c4fb4058-794e-51e6-a5af-e6443b831b1c	50cbf96a-57d3-5f45-b5fc-e415ff57717a	0	to go
68e0bbda-553f-5f5a-b3bf-7d13505974ca	243bdf44-b101-5d5d-bc51-a4f5c1c03bc3	0	to go back; to return
d4a9bc61-0e45-5a98-9ae6-b60e33922a75	27c14160-d0d6-51f8-b7b1-9172cf8b84d2	0	to listen; to hear
81eaada1-9e11-5bea-b007-272bba015494	b8a9c502-6f50-5d50-96f1-417feef69161	0	to drink;
649204e1-57b4-542a-92c9-7b2f9fbb2d0c	87e72b5b-0332-5b61-b21c-ea8cc6a05c1f	0	to speak; to talk
8ed82071-7787-5904-ab49-6847157cb1af	615aadf7-6900-5c48-814a-d6cc0884ae86	0	to read
0ec19188-81f5-5a1c-91c5-d12e747c3f31	bd0e8ffa-f7cd-5b27-8481-6ecb87512f6c	0	to get up
82a14680-5546-5c0b-a995-89982701d01b	28166f6b-3aba-5230-9bd4-18e1a467bc3f	0	to eat
c9602780-2bd3-5de7-9404-f31e4257c6bb	45b4593b-f7a0-516e-84c4-68c7a7099243	0	to sleep; to go to sleep
5010b467-349b-5de4-b53a-c715f3c54f47	66e4cf05-72cd-512b-a462-05f4e69906b5	0	to see; to look at; to watch
60ba9d85-6042-552a-850f-b3524808c3ef	10567940-e3bb-5257-b33c-ae9976bfaddd	0	to come
6d6ccd04-940d-59a9-a56c-52e297210156	5800b221-4eb5-5331-8c6c-413e2d08c007	0	to do
6b9c8ffe-cdbc-5845-9e19-b0f923ac3467	9e9dc42e-df1e-5ace-948a-9dce72caaceb	0	to study
d0372240-a670-54bc-b800-9596743bbc0e	349836a3-2523-5f85-a7c1-f85e49c73f27	0	good
10c983d4-5fb3-59c7-8529-ba34827861bb	fc5427a3-a54b-5c04-8b36-a570b8e7a72b	0	early
7863db2e-58eb-5b39-9429-97cecae473ba	311f8bec-e54c-5317-8880-dfd3e39d23a7	0	not much
74ebb7c6-2940-553c-872c-f139f5f43f00	1e68ea18-9d69-5761-a463-510eab20d1d3	0	not at all
e1766190-9a78-5b43-9122-9aba92664389	818210e0-641a-56f3-9241-7d24504480e2	0	usually
67dc213a-7009-5c52-b2f3-137a2038a98b	b8b492d0-5612-5ac1-bb4d-15f03e959c89	0	a little
a6002c5b-4c7c-5e5a-a59e-44134bcd0078	02115b33-cd19-587a-a3f2-446dea254e33	0	sometimes
9372748e-d8b1-59f9-9e54-cbd79d141211	d7dc5e63-a1ca-58e6-9c31-047e23342953	0	often; much
5907b7a1-cffe-5dff-ba5b-0b0d98cb9320	31df08c1-4538-5a5f-aa61-af9e88805bd5	0	that's so; isn't it?
2985ff92-661e-5168-bc57-ff8d81ae4697	7d85fe8c-2226-573f-a219-0f2def303b50	0	but
d2e220e5-aaf0-5986-b4ba-9e424f18b4ca	4fe504e0-3bf5-5480-98e6-5e626dddc417	0	How about...?, How is...?
8dae510e-c579-536a-b41e-504f3eee37de	6e231f76-5724-5b3c-acfc-cdee6e2eb3a6	0	yes
3f6acfa7-20f9-5e3e-8c7e-d9347be8a018	50ec881b-73e0-502b-8316-3d67e8dababb	0	everyone; everybody
62adce6f-af9c-5fe9-baf4-1a12fb34c61c	f5f775ad-523f-5888-b269-fcb13c1c1051	0	i'm okay; no problem
aaaa1d00-a3e4-59e9-b6ea-b0fdd6c7ebd3	6ae314ed-22ae-5a5f-8e43-c5165e6eb3a1	0	I'm okay; no thank you; it's all right
8d6806d2-796c-5bdb-9862-48f2810aecdd	8640d0ab-1efc-5b18-a49c-564f8e53d65c	0	little quantity; few
\.


--
-- Data for Name: linkages; Type: TABLE DATA; Schema: vocabulary; Owner: postgres
--

COPY vocabulary.linkages (vocabulary_id, preferred_definition_id, preferred_spelling_id, phonetic_spelling_id, alt_phonetic_spelling_id, kanji_spelling_id) FROM stdin;
f552ea3a-8b7f-5433-aa20-4c93a6868fb3	70ac6430-b36b-5879-9b65-bff42d16d560	2f2dd2b0-98eb-5fa3-82e9-27b35f638d92	2f2dd2b0-98eb-5fa3-82e9-27b35f638d92	\N	\N
8359d7d1-d6d6-5995-962e-3f6566232911	f67c3c1b-0feb-51e4-b50e-c580aa54df4e	2e39130c-b534-53dc-81e4-b65ac51ef332	2e39130c-b534-53dc-81e4-b65ac51ef332	\N	\N
4c55fd59-1160-52d2-8383-00f292668c1f	ff0f69c5-fdad-56c5-aa06-a598b8738d2d	7bf03726-849b-5946-b0c1-e426d41b3551	7bf03726-849b-5946-b0c1-e426d41b3551	\N	\N
8d46e404-38ff-518c-a25d-cb95d6f2c511	aa9ebe48-87ca-5d72-9fae-6ab3d3cb5a41	f5c3d443-f368-5c17-bc3e-565c4e3be6ca	f5c3d443-f368-5c17-bc3e-565c4e3be6ca	\N	\N
1184380a-27db-5f8e-841d-7b105c2a7366	b00a7fa2-3a44-55d5-b4b6-55bb0d615cd7	5db7f2a8-e3dd-56e0-8e19-3349d92b0aea	5db7f2a8-e3dd-56e0-8e19-3349d92b0aea	\N	\N
49ab0509-4a86-552d-975b-fc49e74790d4	a3b4604c-0b6a-59b4-b582-0e5c8e962f10	32066cdd-df5e-5ef7-a364-9bb86df93b69	c24ad673-299d-55f9-8f78-7717388ff80e	731f72c6-1431-55c3-b1c2-563276f2b387	32066cdd-df5e-5ef7-a364-9bb86df93b69
59d58d35-f0f2-5535-8404-8cb786e2c830	7ce6f95a-1009-5be0-b599-de6fa2f9347e	244332d4-fe91-5051-958f-ad2b62370f81	ee62d46d-0f52-53d3-b866-efba4650aa45	ee4bbc0e-84f8-5e9d-b10b-53c05de51a1c	244332d4-fe91-5051-958f-ad2b62370f81
c7a645f7-256a-536a-a5ee-5f85e8ce1137	ee0d82ff-fb58-505d-8b3f-8470835f55bf	70b3ffdd-dec4-5d99-a8de-2553945a745b	70b3ffdd-dec4-5d99-a8de-2553945a745b	\N	\N
b626308d-6418-5a2c-9151-7a8a368f27a3	7b689a5a-2866-5afc-98ca-0bdaaf095f54	8181b875-2569-5bee-89aa-59645c7068dc	8181b875-2569-5bee-89aa-59645c7068dc	\N	\N
507092ff-6dc3-5749-a9c3-79ba8d2bc360	6940df74-f3c9-5f8b-91a1-581ae175d8fc	94211400-d68e-5f0a-b5ba-724ea1b1f285	0da5c7c8-ad23-5957-859a-42f77031451f	cb73f875-d21e-585c-bb5f-3583315a2ac7	94211400-d68e-5f0a-b5ba-724ea1b1f285
e7161fcf-fbaf-5971-8adb-70745ae24103	88041d5e-1d65-5fc8-b03c-f715ba4ea0df	de8a0b88-aa07-505c-8c52-76e3d33d883b	de8a0b88-aa07-505c-8c52-76e3d33d883b	\N	\N
c2a4dbcf-dba1-5e27-964f-5e2f7b358725	d55290c9-3c73-59ae-9616-c973808fc680	2dacefc6-1027-5cf5-9b72-a73fc09d2e01	2dacefc6-1027-5cf5-9b72-a73fc09d2e01	\N	\N
59bad243-8b31-54f3-9d7b-6de0961bc059	31bcbff4-fb38-56e5-a7b9-08120bf11acf	fd5f67e7-6779-5496-b121-7a85d321eb98	fd5f67e7-6779-5496-b121-7a85d321eb98	\N	\N
05436324-1858-5a2f-a2a1-a41d00f862cc	7719c179-8686-5442-87cc-1e6e95c174ef	4e531b79-f0cf-509a-b5b1-3b94046d5bdd	4e531b79-f0cf-509a-b5b1-3b94046d5bdd	\N	\N
136d6d6d-0487-5709-ac27-2c376bfebd43	15e0d3c4-823b-5f7c-8d2f-1815335f67dd	952c45ee-cad0-54ed-a463-073236117849	952c45ee-cad0-54ed-a463-073236117849	\N	\N
c26e8356-f73a-5ddf-b045-432580c37468	65c20de6-3266-50cc-8687-4a015bc6764b	d955293c-2989-58f4-9e0e-ac989f6c809a	d955293c-2989-58f4-9e0e-ac989f6c809a	\N	\N
522e0796-297d-5bf2-be85-e93596baa6bf	38eb464c-6609-590f-88d1-bb1f634dd591	f8e54dc8-4fde-569a-9bd2-cc10924c1197	8ef18341-fc94-597f-8c35-7a525fec4d50	\N	\N
84e857b9-5605-51fb-bfb3-8cc08eb579d3	b1d437ff-bad4-5d94-972a-6315eb3e545f	42c5cc87-b98b-5f1e-86c7-1efcf1836bec	42c5cc87-b98b-5f1e-86c7-1efcf1836bec	\N	\N
dde2be42-c934-53fc-9dc6-3ce12b727924	98aeb9e6-1da7-51fd-879e-13a54f1538b4	2b7913d9-04af-52d8-a8b5-e49b2a7f3628	2b7913d9-04af-52d8-a8b5-e49b2a7f3628	\N	\N
0428342a-6b57-5e6f-b1c6-4aa6ccfdb267	620810c9-2712-549c-af3d-1ac92a8ff0c1	200e8dda-038f-5fe5-b1f9-d83a539f9e4b	200e8dda-038f-5fe5-b1f9-d83a539f9e4b	\N	\N
a48cfff2-551b-5525-bf29-f706571e7e15	046e8f71-d4c3-5d2f-b2ee-30abd28bc9f6	88bc0db8-206b-504f-8db2-f1435e733a21	88bc0db8-206b-504f-8db2-f1435e733a21	\N	\N
311cf3c0-5798-551b-a933-eb33c4ce7843	c97665b5-9732-5584-82a8-9406fe17cd64	fb06d772-7e41-52e7-a23e-dfdd80ab45f0	fb06d772-7e41-52e7-a23e-dfdd80ab45f0	\N	\N
40483fe8-7fda-56b7-b28b-eedb9da50a52	009be582-f140-5869-8984-eccbc918a36f	08c18f75-25e0-577f-a2a9-1d95d4ff1a56	df0854bc-e9f0-5ab6-9a57-c2cc9f7ffecb	\N	\N
2a0ff39c-471b-56d1-a2b2-39f20596f70c	6b95c105-bdc0-5cba-a630-095e120bcdb3	95f1289c-15a1-54c6-9220-2840d7e9d9bc	4a20975a-64ea-5aa7-9168-86554d3891e9	\N	\N
b4a146e5-f483-50d5-8b18-ca71fdb3f570	8a4b908a-0b93-5c53-840f-cde8e5f4df5e	083dbcf2-4cc9-57b5-80d2-a5b50d62f1a0	05f45690-8e1d-5f99-8d69-59e7ec26a9f4	\N	\N
f3f3ba33-de3b-5fa1-9b99-eee62ae2e024	4f78cc1a-6d5c-53be-a8de-22ba07ac8eac	00f00677-c3f9-53d4-abe1-b437b2a34eb6	6be06087-8a55-5aa4-9a5f-c3bf69eca487	\N	\N
eb20360e-5c1a-58b8-9bf8-040795c5bf0a	e9a4005c-2a81-5874-9f60-be17094fb1a8	d1877ff8-19e0-5a7c-8c8e-eb840d38d144	931fb016-2c54-59d7-9772-285ca5b85264	\N	\N
ae144a7c-293f-5f24-81ee-37d5594d6aed	f70590ac-0c45-5376-8012-fdf0e8088d66	9bb2367f-e815-5505-baeb-efdd53ccf5cb	e2d3accb-0079-5258-9ea1-235f0a5dc6d6	\N	\N
9e62feab-62dc-5faf-92d9-8cd423af7dab	8e9daa8d-f29e-5a32-972c-b2b13836ed7e	39ca3b74-f47b-55bf-b3d8-611f8b253473	25a0153b-54e0-56a9-9f79-5b9e66aa8281	\N	\N
a74f0164-da3d-59dc-bbd0-39cf065ee813	7c25111c-2a30-5205-a5db-a539ab6c798d	799f7b6a-056b-5c19-913f-25ed551f5a80	882e2ee9-48be-55dd-80e7-9f10bc83a978	\N	\N
58ecba76-692b-5baf-a096-62bf3e752da2	dd943cf5-3d3c-5f13-a3cd-6c51867bada9	a6108ee1-eb7a-5638-aa7a-4185eb24ea38	a6108ee1-eb7a-5638-aa7a-4185eb24ea38	\N	\N
8373ee97-fb02-5fa4-9f3a-e6d0bfe42042	b3b9b81d-b2af-5306-97f1-1e2ef5db0333	882616f1-e948-57d0-83c8-5547632e09d0	882616f1-e948-57d0-83c8-5547632e09d0	\N	\N
e3286acb-a31b-5fb6-9c16-28ac16fcc269	2fd8013f-de54-552b-bbde-6fa0c7f43454	ef19ddc0-c4d7-55f4-89f1-60add525833c	ef19ddc0-c4d7-55f4-89f1-60add525833c	\N	\N
9ef8e062-db86-5059-82be-ccc85580d5e7	e674bc96-2f20-53a1-aa5e-aa527d21e056	d27869d8-0951-5cac-8b3c-b6dd33774d56	d27869d8-0951-5cac-8b3c-b6dd33774d56	\N	\N
430b4ea9-6092-52c7-b2bf-4300de6196c7	14b5fe84-4752-5db6-b3e6-21c9891951d8	859e7825-dad8-599e-b1e3-7200317f683b	859e7825-dad8-599e-b1e3-7200317f683b	\N	\N
9d9f54be-2a19-5147-8935-e7c3c887fa03	7044c077-2e58-5141-be32-5f7aadcecbce	c15b0e83-fa7c-51e5-918e-fe84c939c64e	c15b0e83-fa7c-51e5-918e-fe84c939c64e	\N	\N
908efe1a-b6e9-55d5-b550-461cea175af4	4b1b31e1-528c-5c99-8fcb-22eae553d2b5	e58ad2ce-ea60-5c13-bf9a-990479cbc401	e58ad2ce-ea60-5c13-bf9a-990479cbc401	\N	\N
a9b7cffc-2d14-58ae-818b-3ac3b6e6e4d0	3c0f2f7d-d5e5-5369-93c8-50129680fdf4	967de66d-2c31-5a9e-a0c3-53f055b947a2	967de66d-2c31-5a9e-a0c3-53f055b947a2	\N	\N
9c591bfa-c4a7-5263-b2b1-7c8d14e10c34	16022cbb-94a2-53e7-9067-9682e1e53b85	c5c9d413-7ee6-5382-9fd9-90bfacc2a0ea	c5c9d413-7ee6-5382-9fd9-90bfacc2a0ea	\N	\N
0279527b-c398-5856-aad3-11b834907f56	3a496e65-9d8e-571f-88ef-a562e79495ef	3b61d4ce-617d-5d28-b2a7-e5b3ba22aec5	3b61d4ce-617d-5d28-b2a7-e5b3ba22aec5	\N	\N
5587745e-e524-5159-8fe6-1e8637b90455	08023337-313e-56e1-a77d-44ad5b3f2d8b	65ea426d-2bbf-5cc5-87dc-5aea98c944be	65ea426d-2bbf-5cc5-87dc-5aea98c944be	\N	\N
1502a2cb-059c-5a9f-baab-af68a0d09ac8	c1731080-65e8-5a77-bad7-5bd296dff087	cc558a9f-b656-50f4-a28d-9f423dabf927	cc558a9f-b656-50f4-a28d-9f423dabf927	\N	\N
3ab19c55-4c6a-53e0-afce-41968363d92a	70aff8d7-e756-5b41-bcf8-e0979ff802c3	50cf4cc0-608f-51a6-8d56-c46ff556040d	aaf68fce-fdf4-54f7-9cba-dd73ea2d112a	\N	\N
b74ca7fa-4622-5a52-b9df-5260878e687b	e43fe162-0590-5f49-949c-dd364151e3db	3b766a57-2e25-51fe-a01b-df14fd28384a	2ff20629-f937-5c9b-a542-7c63dd107997	\N	\N
bf5e0762-effe-5d38-8786-503cde98e504	5880f681-5fce-5271-8fdf-ddff3d293edb	7654498b-681f-5f20-a8f6-d363ba399dea	7654498b-681f-5f20-a8f6-d363ba399dea	\N	\N
e7323b54-9fa5-5cb7-8d68-d2ebe9b379ea	4ef95b9f-604d-5bdf-b02a-3415a8b56fa4	1a323805-e9bb-57c6-851b-45525dba8f69	1a323805-e9bb-57c6-851b-45525dba8f69	\N	\N
1a0585e5-3bd0-5903-90cc-d3381778da68	1a0e03b4-8b99-53a2-92bc-202ac3662891	a8548ac9-a03a-50d0-88c2-2c96c8048dd2	6abd1ce0-a561-5b69-8720-e941239e0050	\N	\N
612eac67-3ec3-535e-94a3-249d60f51046	09caa815-f519-5288-bab2-8c686e70209d	70964b04-78fa-5557-966f-cb4ad75b7db1	70964b04-78fa-5557-966f-cb4ad75b7db1	\N	\N
442d1c8e-a848-5fa4-8fc9-d398441b4b65	2bf8e240-348c-577d-8379-0e9aaedfd052	3314b6b4-06a9-567c-8f20-9cc070af51ab	3314b6b4-06a9-567c-8f20-9cc070af51ab	\N	\N
6c108fe0-b52d-56de-96f2-f8e0bf450367	5ba48d55-a102-5aaa-9c22-c148423244c5	23c3b38a-7ddb-5fa6-bca0-246662b6b6a8	23c3b38a-7ddb-5fa6-bca0-246662b6b6a8	\N	\N
14edc8de-8ed3-58f5-a083-13a34db6f490	8fadeb9d-8ac2-52b7-ae99-9ebf12c82f03	fc74efc7-2d6f-56da-9b69-67895103219e	fc74efc7-2d6f-56da-9b69-67895103219e	\N	\N
1cee44df-fc33-5279-8d09-5051e714782f	15e296e3-94fb-52f3-b3a9-9ee9eb2bb6a0	9d6976b3-1495-5b61-aa0a-12eaf307c5e0	9d6976b3-1495-5b61-aa0a-12eaf307c5e0	\N	\N
fc6be215-8cf3-55b8-8a6b-0dd345852963	be7c4dac-9506-5253-9d4d-014761f015b0	6db532f0-8d3c-5767-8e4b-9987eff9d49a	6db532f0-8d3c-5767-8e4b-9987eff9d49a	\N	\N
3bb29baa-6e40-5c58-93cd-2000ea1120eb	dc8c73bb-3ef7-5159-97ca-e0780fba9926	3f8143f1-d336-5115-b5d4-d1439274829a	3f8143f1-d336-5115-b5d4-d1439274829a	\N	\N
8dc54b41-91cb-5289-bb0b-5f4a5854fea7	b225b075-4310-5d12-93dc-d85956e285da	4733d6d5-c7b2-5c75-a20b-b7182d3ca297	4733d6d5-c7b2-5c75-a20b-b7182d3ca297	\N	\N
5891a34d-ba0a-5458-b42e-f82c60d27f9f	81cd1a87-2d8b-5439-8a97-0c236c2753b5	7f66db47-afc1-5499-b196-5478b7736279	7f66db47-afc1-5499-b196-5478b7736279	\N	\N
48b8587c-d363-5b54-94d9-c9e5dfff2412	afe65193-cf68-561b-a5cb-e35b7b496b31	29140c19-30c1-53aa-b840-9a8dd9b4f3a9	29140c19-30c1-53aa-b840-9a8dd9b4f3a9	\N	\N
82339058-2123-58a8-93dc-b1c0b05cdae8	4557611d-face-5211-8356-f37f26dfd718	cb3e072b-a2c0-56a6-b10f-498f6d1c5b81	cb3e072b-a2c0-56a6-b10f-498f6d1c5b81	\N	\N
bbf5a302-a23c-5142-b721-2aa90ff1e8dc	ef6e20f2-87ec-523c-94e3-f9fe3fadd8dd	03f21ed6-b87f-5881-999d-a9c2a44cd852	03f21ed6-b87f-5881-999d-a9c2a44cd852	\N	\N
738002f3-36da-5668-8a8b-46415de8cd52	927f9ade-e660-59b6-b454-7837ea418f62	cd5d9ef0-6238-5b4a-82f5-45bfa4cb6268	cd5d9ef0-6238-5b4a-82f5-45bfa4cb6268	\N	\N
8f790b97-9843-5efe-b338-e1c37618c616	2bb8e10b-3a10-5ae7-a8ab-697917d98563	a64a5d7a-2ee9-51d9-837e-4adef2eff2fa	a64a5d7a-2ee9-51d9-837e-4adef2eff2fa	\N	\N
5ca8a07b-ac34-5789-ab4a-8bd29d51929c	e0f8777a-8c58-5cf0-9ad9-dfa2fdc145f2	567ed735-4a66-5e94-bbc8-5090fdf1d0b8	567ed735-4a66-5e94-bbc8-5090fdf1d0b8	\N	\N
b3e732b3-61c4-574d-8901-b1c74076b5ea	20908f2f-a090-56d2-801a-fbedbea38f16	611ffb13-c0de-5e82-b9e8-8e6aef023163	611ffb13-c0de-5e82-b9e8-8e6aef023163	\N	\N
720a22a5-3132-56e6-8380-03a20e2a4af1	9d3a9b01-8f1b-5d20-a97c-6549adc84042	5a316e7e-9528-5378-b9bd-dc9cbb9ea94a	5a316e7e-9528-5378-b9bd-dc9cbb9ea94a	\N	\N
c675acf7-4ab1-5a0a-beb3-e65aff83932a	5e3e2e75-7e8b-555f-99b7-64b49b06c936	a110bba0-64d5-5aff-8ff6-db02a4456030	a110bba0-64d5-5aff-8ff6-db02a4456030	\N	\N
c5ebd7d9-06b2-596e-96f5-7366810ea78c	e04ba1c1-ec1d-5b71-8707-00b2d9639ccc	f76ccfc2-e037-53eb-b589-84b398ac85bc	f76ccfc2-e037-53eb-b589-84b398ac85bc	\N	\N
f05e2adf-83c4-5ccf-a74a-fbd5664dceb1	f816ed1f-c8bf-5d48-909f-76f27d406879	24a8a0dd-b581-57a3-a312-50b0e44d8cf5	24a8a0dd-b581-57a3-a312-50b0e44d8cf5	\N	\N
5800b221-4eb5-5331-8c6c-413e2d08c007	6d6ccd04-940d-59a9-a56c-52e297210156	c32694ad-c718-54cc-9465-256f9d59d0f8	c32694ad-c718-54cc-9465-256f9d59d0f8	\N	\N
349836a3-2523-5f85-a7c1-f85e49c73f27	d0372240-a670-54bc-b800-9596743bbc0e	ea974989-8c3e-5a37-a38b-54463cd75e2f	ea974989-8c3e-5a37-a38b-54463cd75e2f	\N	\N
311f8bec-e54c-5317-8880-dfd3e39d23a7	7863db2e-58eb-5b39-9429-97cecae473ba	0ae8bfe8-5aed-5bc1-b505-0eed73a461c8	0ae8bfe8-5aed-5bc1-b505-0eed73a461c8	\N	\N
b8b492d0-5612-5ac1-bb4d-15f03e959c89	67dc213a-7009-5c52-b2f3-137a2038a98b	c8550df0-3aeb-5cc3-bc1a-dc4cb78b4528	c8550df0-3aeb-5cc3-bc1a-dc4cb78b4528	\N	\N
d7dc5e63-a1ca-58e6-9c31-047e23342953	9372748e-d8b1-59f9-9e54-cbd79d141211	5d59387c-87a8-5428-952f-1762e0e224cd	5d59387c-87a8-5428-952f-1762e0e224cd	\N	\N
31df08c1-4538-5a5f-aa61-af9e88805bd5	5907b7a1-cffe-5dff-ba5b-0b0d98cb9320	23aed903-bbee-5860-bb46-0625fcc3a740	23aed903-bbee-5860-bb46-0625fcc3a740	\N	\N
7d85fe8c-2226-573f-a219-0f2def303b50	2985ff92-661e-5168-bc57-ff8d81ae4697	d9c81727-6c65-526f-9592-e3d0c3d4ccee	d9c81727-6c65-526f-9592-e3d0c3d4ccee	\N	\N
4fe504e0-3bf5-5480-98e6-5e626dddc417	d2e220e5-aaf0-5986-b4ba-9e424f18b4ca	2eedf85e-4cb8-54e6-a3da-6bea09e2c6dd	2eedf85e-4cb8-54e6-a3da-6bea09e2c6dd	\N	\N
6e231f76-5724-5b3c-acfc-cdee6e2eb3a6	8dae510e-c579-536a-b41e-504f3eee37de	da051662-bc91-5d51-99e8-2a60f04b04ae	da051662-bc91-5d51-99e8-2a60f04b04ae	\N	\N
50ec881b-73e0-502b-8316-3d67e8dababb	3f6acfa7-20f9-5e3e-8c7e-d9347be8a018	3f28b04b-aee2-5c5b-a69e-75c4c0bb906e	3f28b04b-aee2-5c5b-a69e-75c4c0bb906e	\N	\N
3ecfb07b-0a66-555c-97c4-5af7cb806507	753a8f8c-a381-5612-9a2c-5f0b1375dca9	b00cb6d3-68c2-5b23-94e3-89d121cf9f7f	b00cb6d3-68c2-5b23-94e3-89d121cf9f7f	\N	940a59ed-edfe-5908-87c9-421c4a74910b
a419bb62-f061-5ac6-856b-6f85002642c6	32bd2dc7-78a3-574f-a1b9-f1806e6d89b0	853d8a20-9dd3-580f-acd3-0cbf1994afc1	853d8a20-9dd3-580f-acd3-0cbf1994afc1	\N	b9e54157-032b-5a34-8bf2-4a695a174e95
1913dbc6-4712-549a-9274-dc3511cecbfe	48f53540-7686-5ab3-8de4-6384fa259cbf	9c08a5c4-c6f7-5ff6-9bd8-351cd2e0bea8	8eac0d70-9234-521d-998f-ea30ef7bce05	\N	9c08a5c4-c6f7-5ff6-9bd8-351cd2e0bea8
b06abb09-c9bd-5beb-a7b7-d184bedf72ca	11614ea6-fab1-5e3d-b72c-779848bb02b6	0d45707c-2c15-526d-8eb5-dc5d5bb498c5	dadba29c-5f6a-50b0-9ec7-1d957ada3ca0	\N	0d45707c-2c15-526d-8eb5-dc5d5bb498c5
47b08527-885b-50bb-b538-180dc308199a	c199c713-3494-508d-afa0-4cdb9281d245	388f9709-4b78-5e37-b4be-ca1f340f80df	03b1739f-0b9f-53fe-bc35-bfcc6cc2e00c	\N	388f9709-4b78-5e37-b4be-ca1f340f80df
797b03e3-1495-52c0-baf4-ffb9cb324089	1001d407-e258-5e91-9ff1-0109995e9373	83f63ec1-9a22-5b1c-ad6f-bdfd29122950	ecf2fcf6-dd4a-5c84-b057-7de027c3b163	\N	83f63ec1-9a22-5b1c-ad6f-bdfd29122950
ddd5fd1f-f041-576c-a479-d8d9a3221370	ee0ed371-fd24-5411-b704-5af7b247db99	89245ad3-f3e3-5533-a7ae-61a976b44360	b4530b71-39a0-5a3d-9130-db16a0ee944c	\N	89245ad3-f3e3-5533-a7ae-61a976b44360
d43cc787-37af-5dd1-92b5-fc756f18298a	1740fbc3-92a0-57dc-bf04-7670587710b0	e4968580-e81e-5df6-8133-9714aeee0fee	7720f5d4-fb34-57a8-8dff-ce224814165f	\N	e4968580-e81e-5df6-8133-9714aeee0fee
3c890a54-15f9-56e1-9bd5-3c91274b6246	ed658793-513c-538a-b413-76e1565f1c47	17e0efb1-d7a9-5d07-b80b-9e37848b6ea7	4bacb2b0-df5c-52ba-9f0c-e6a9ee44ed8b	\N	17e0efb1-d7a9-5d07-b80b-9e37848b6ea7
6de86986-05ed-58da-a055-9d49c89f7a06	abd9699b-4a0b-584f-9aa9-34f5ad747446	26c33f84-32d5-5009-9667-2e36f7c76f93	505c6e26-f11f-5a47-9047-25b9ec0453db	\N	26c33f84-32d5-5009-9667-2e36f7c76f93
d1cb9e5b-6cc5-5339-b33d-4e948229156e	81b959fa-48ef-5c9c-ab21-fd42ba234894	9c4e6d38-f55b-5b31-be2e-9a160cd11a75	e0620b0f-0e03-539c-a48d-f6ed5e9b211e	\N	9c4e6d38-f55b-5b31-be2e-9a160cd11a75
efe5fea4-0476-5328-bd18-2a469a8b6c3e	7213f37f-0e8c-5ca6-833c-b27ee386b797	92fb378c-061e-54ea-88a7-26ccd8cdfd3e	cb3e03c2-0f63-59c0-bf4e-9b0154c72a26	\N	92fb378c-061e-54ea-88a7-26ccd8cdfd3e
c21b42a5-1ef1-51cf-9402-199d2b562a47	eceee346-c010-5e88-9b4c-f5ab85a02acf	1f548470-05f6-57a0-88d2-f70838631980	88e40a78-7bc2-51ed-90db-1aae50653bd0	\N	1f548470-05f6-57a0-88d2-f70838631980
057a7cd1-f1a6-50e2-8449-3b6f04c67305	9cf13027-c124-56c6-b31f-8651d4bd244b	9b33e54f-79d4-5482-8188-5bf97515192c	fc65939b-8ffe-563c-a8d1-b625ace6f3c0	\N	9b33e54f-79d4-5482-8188-5bf97515192c
e71aac30-c949-5b47-b118-d02a5d703701	3a782f59-e890-5100-8892-36908ee3bcc8	313fc7dc-5e8b-5f8a-b0ef-c4a7c4b27ca7	9f378893-c062-5f8b-872f-f1190abac310	\N	313fc7dc-5e8b-5f8a-b0ef-c4a7c4b27ca7
4b1d8b7f-6477-5881-9439-1a7f30b26c9d	289c8d20-3dae-52b3-83d5-fcedc55ec6ce	af1d66c1-3693-5244-bc3a-379e1a79da8e	604c6906-683f-594c-9b2a-b84e7f95d4a0	\N	af1d66c1-3693-5244-bc3a-379e1a79da8e
f65b93fb-1086-5405-8163-394db3e83014	9d503e44-704a-5102-bf2f-e3dd0dc3f79e	5df6641a-0be3-5524-899e-6fa4d0ad962b	467c7d80-dee6-531e-8017-e553c5024e38	\N	5df6641a-0be3-5524-899e-6fa4d0ad962b
0ef6972f-5e7d-5b51-be7f-8f39a5fd5e0e	98cc142d-1e63-5134-941a-4ac349a6c9de	b476718e-1352-5d4d-ba7f-40533eef9335	3d509818-d77c-553a-9b0f-dbe7c0a7baa4	\N	b476718e-1352-5d4d-ba7f-40533eef9335
146b7705-d9cd-511f-988c-5b2cc7661b75	675a7b2d-a38b-5f86-b3a8-3a8753a57133	3a927707-ed22-58af-a172-c9fa19381777	3a927707-ed22-58af-a172-c9fa19381777	\N	509b8b44-bd6e-5c04-a3aa-67a090c82798
8bedfb60-1599-58b0-9568-5e72b8cf6b7e	c2a55006-9ec2-59a5-99bc-99385a898aab	660ff6f5-d27d-53a2-8a04-dc3bcdc8a92d	088067a4-7d07-588f-9c06-c67ac32882e3	\N	660ff6f5-d27d-53a2-8a04-dc3bcdc8a92d
25f93b07-dc04-553a-8794-329cc907dbe5	d929ff1d-1d63-5290-9ed7-86913a847aa1	334c5e68-696c-578c-916d-010364f768d7	20ed3904-409b-5c09-b335-6309e82f3835	\N	334c5e68-696c-578c-916d-010364f768d7
37eba762-70cf-5c5c-b689-621f26c1ee5f	dc7e50df-0ba6-59b1-b00e-b596fef35437	1778e2d9-1209-5d58-9a55-d85c3878cdc2	60bcf3c1-74f9-574c-a85b-04a679cccede	\N	1778e2d9-1209-5d58-9a55-d85c3878cdc2
99cdf9f9-df8c-5d7a-9ea1-a7c003c9f289	b2576dd7-bc99-5998-8b21-99298c003eff	fd0c12d6-2acb-552a-8db5-ae67d30d39ec	d0d158d3-a7e7-5a96-9c5a-e8f231144bb3	\N	fd0c12d6-2acb-552a-8db5-ae67d30d39ec
947e6f87-e4fe-5b27-9bd3-215e8b1fc71a	242c7787-c6a1-5cba-85b9-71d7f0098e85	41a1e283-3de2-5e69-a35e-f256c427e4ed	41a1e283-3de2-5e69-a35e-f256c427e4ed	\N	41a1e283-3de2-5e69-a35e-f256c427e4ed
dd191fac-e29b-5262-b2a6-f9f2875a4a3f	066fd172-a3e1-5ee2-b33d-824ab48c91e5	c02490fb-2215-5d4a-9e20-19aa694b99d8	5f4854d0-f405-5aef-b032-b78a489a6e30	\N	c02490fb-2215-5d4a-9e20-19aa694b99d8
fc82f2ac-3b17-582f-9259-7200e1430824	f0725114-391a-571d-a983-1dee80598a28	e1592633-21b6-52c9-a445-6fcae3f630f9	3de7d669-5ddc-5841-8a5a-bcaed0a139db	\N	e1592633-21b6-52c9-a445-6fcae3f630f9
15c5fee6-dda7-5d40-b7fd-382d97a28b9c	e9f50173-81af-532a-b840-e9ad03c23231	58c2bc1c-4af0-552a-b348-3b7e50afcfee	3ef6a3f6-bbae-5ff5-8133-2792969d4f5a	\N	58c2bc1c-4af0-552a-b348-3b7e50afcfee
39458010-a5eb-5b13-b054-4cc458702c56	3c9671c0-0b3d-5fc6-9456-69c968da374d	ea87f8a8-b40e-594a-bc78-dc29296cb1f4	e47dc2e2-4bdb-5632-9302-ceffdec49177	\N	ea87f8a8-b40e-594a-bc78-dc29296cb1f4
091d6d9c-5d19-51f5-bc28-3b5281042464	d8a0901e-ad81-54e8-886d-1a7c5bdf1281	5bed6ed8-0d3f-58ae-86af-81af9ad50ff5	632bf264-1e66-5000-8173-750a6d991040	\N	5bed6ed8-0d3f-58ae-86af-81af9ad50ff5
6e44df4a-4366-5c1a-86af-87a4e5f61fc6	9a2bb1cc-bd0c-5df7-ba66-9039b769b9f2	7c6a7f92-02ab-5e94-ad8b-f949dbd917c1	19f013e0-d32d-5ece-a530-e4ec8cb4f4fd	\N	7c6a7f92-02ab-5e94-ad8b-f949dbd917c1
7748dd60-2126-5e30-a5b6-39596a0e25e4	8af6616e-3d26-5b2c-9e16-d3cf99ff4340	3a426749-0a9d-59a0-b31e-163325029c38	a51b7ff7-dacf-5be7-b44c-e5da68ed7b5e	\N	3a426749-0a9d-59a0-b31e-163325029c38
dce3fe6a-497c-54b2-a121-df6767084258	7a1f4917-96ab-5ebe-adb4-89b96fe27664	2db138e0-296e-57af-b161-a0841bd3b1ae	78f51be9-33e6-59e1-b1f2-1a34ceadf88c	\N	2db138e0-296e-57af-b161-a0841bd3b1ae
42ffdbae-c27d-53a4-b113-95583953e16c	a0f9d2e3-9d86-57a2-93e8-9377bcc14f29	c3171fe5-314a-5e45-8731-9abe72811c9b	46cdc645-d4e5-590c-b1c9-0fdd8df485d4	\N	c3171fe5-314a-5e45-8731-9abe72811c9b
94611381-0488-58aa-8cf2-12ad9ff4e2dd	720c7553-f414-50c1-bf89-61b4b9192117	c13b47d3-6e89-5265-b429-7e805315a56a	6b73bd8a-1121-53da-a698-bdf1c1a54bfc	\N	c13b47d3-6e89-5265-b429-7e805315a56a
40502e18-b4ba-50b4-89b7-23fd3b4d4a21	6ff0b4a8-8a4b-5875-8934-99edbd739a9c	64633680-dfdf-5c40-bf59-c9a18d1c5591	b6307fa4-e750-5072-9cc1-456072b23a86	\N	64633680-dfdf-5c40-bf59-c9a18d1c5591
5372b023-b803-551c-9a7f-f404804f63f1	9baf2625-b152-5c51-b81c-9fa26799fb6a	e95ef123-eaf8-569d-825b-be051e27e267	87ce5bea-03fa-5a04-91d1-e7a95db235a4	\N	e95ef123-eaf8-569d-825b-be051e27e267
2cf1d32f-b261-5ba6-829c-a9f1237a88d6	33383647-612f-5d18-b723-7c531eca7c85	dd94312b-40ad-5d97-bffb-e4feb0b76f5e	ca4b692e-0867-5fd3-bfb2-ce881f2a697e	\N	dd94312b-40ad-5d97-bffb-e4feb0b76f5e
a39e794c-9682-5cd5-9c5e-dd961aa92bc5	53a4bf51-dd4b-53f0-bed0-991cd6c6c747	227a4c31-bb55-54a7-bb05-80093a7cb329	227a4c31-bb55-54a7-bb05-80093a7cb329	\N	67e21d4b-5197-511d-9c3b-598da51ddcba
9e67a677-9058-536e-85d6-389ea5138c86	a88eb384-641a-506e-8a3b-ab52828c4057	81f214fe-f9c4-5745-9c04-1e0b87dd62fa	81f214fe-f9c4-5745-9c04-1e0b87dd62fa	\N	976d29f9-bf23-5edd-ae40-d759c27b57db
252852aa-aefd-504b-b671-668a33020492	eaebfa66-8524-5e12-98ac-e36bf99b3d2b	7aa45f0a-1602-55e5-8ede-e5a4821ffe14	cef316f8-c889-5b71-9c54-d543f35d6746	\N	7aa45f0a-1602-55e5-8ede-e5a4821ffe14
cc6a4913-6f39-521b-a0cf-9631f640800e	75badafb-8301-527d-8fdd-91321a9cc2fb	da8cdd76-87ee-5d35-9ca2-5340654a1069	5c7b43e0-ee24-5a43-a234-de4e64db32bd	\N	da8cdd76-87ee-5d35-9ca2-5340654a1069
a420a37b-593e-572e-aae7-d724eca6803d	4972f7a4-6d55-5486-aebb-1bbb2c8c68ea	a7e5fa4c-e482-5b11-a2e7-478283493467	80addbcf-6579-5145-b127-7e712b37587e	\N	a7e5fa4c-e482-5b11-a2e7-478283493467
c59169cb-e5ad-5311-a9ce-43764a5ddda6	6121bd57-f362-559e-9bc1-cfcbb75a14fe	8ec71c62-6b1a-5b96-bc80-f8299a8ea96d	f263cfef-ae70-5d80-8789-cb00ee27219f	\N	8ec71c62-6b1a-5b96-bc80-f8299a8ea96d
1731f988-dcfb-5b31-bda9-c8988a5711bf	a42680cd-c376-5291-a8b5-d777ad4e44d9	ab67f8fa-6dc7-5388-8835-dcc9b3eb8cf0	3804b3b5-a865-55f8-ab40-d1dd5e7819da	\N	ab67f8fa-6dc7-5388-8835-dcc9b3eb8cf0
f6524cfb-fa66-56ae-ae4c-0e38a8b6e54c	a2178e5a-c9dd-5dba-aee0-adfeb6e59141	522dc3a3-3c47-50d8-bf67-33f6598d9d15	b08756e4-b14f-57df-999f-2425e70e603e	\N	522dc3a3-3c47-50d8-bf67-33f6598d9d15
d8f98686-282e-5c5f-88b2-4e046e707e54	98122d83-a56f-586d-b3ba-6126b5879628	eb6016b9-819e-572e-a45b-55673bcb2ef4	e7af3ad4-b7eb-5aaf-b2ac-946c4609211d	\N	eb6016b9-819e-572e-a45b-55673bcb2ef4
728f5a87-3848-5450-a941-f9cd0f898067	5d6b95fc-4aa6-5805-93f3-f3a36b6e11c7	f6b82f79-75ee-5c63-a532-f258ce7d6f0e	d71ea796-b5fd-55e4-9cff-38834b897d0b	\N	f6b82f79-75ee-5c63-a532-f258ce7d6f0e
5cb08712-5c04-56f0-aaf6-a8e76a1e0ef4	95234b6b-a89b-5231-b9a5-4f35c4419035	eaf53b3e-7a17-53cd-877c-b4ea789d2ca5	158c5216-a31d-5cc5-b554-5d6d6cc033a9	\N	eaf53b3e-7a17-53cd-877c-b4ea789d2ca5
54ca6cf2-c9f0-5a77-b53c-8e4a2fbe76ac	35eab8ff-4a4c-56e4-9acb-8bf93f633927	dbbac175-c623-577a-a7ef-4c9c815950f3	62bf6317-c9f5-53d8-84e9-c208cc4689f1	\N	dbbac175-c623-577a-a7ef-4c9c815950f3
abd1e50d-f284-5a21-831f-344cb338414e	b6ad77c1-fcbb-5b1d-b163-5783ed1fbc28	227c9697-18eb-5d28-97b4-dac3315cbba1	2949e58e-ec62-51a1-b054-c173a31db85f	\N	227c9697-18eb-5d28-97b4-dac3315cbba1
13912d04-d947-5c72-a192-740c19a28c86	3280bf28-482e-5a77-93c1-59e5544438a6	42d60a40-2739-526d-9aa2-f143b84cde77	88b80e93-dda8-5699-839d-3c204c735d20	\N	42d60a40-2739-526d-9aa2-f143b84cde77
7672ef18-d91f-548f-a938-970d6a741586	0cac7dd2-6b30-51a3-be00-9d85db1527a8	612e9cfd-044b-5525-a286-ce4b2cb9639c	c85d20ac-6223-5d18-95a4-3b2e5696d50d	\N	612e9cfd-044b-5525-a286-ce4b2cb9639c
471eeab4-7852-53f9-ba6c-342f13746958	199f07f7-392c-5a2b-be9e-9a719a7df0b0	f30e248e-f63c-5f76-b101-affd50e0d861	a565cd3e-3048-50de-b733-d6bc57653492	\N	f30e248e-f63c-5f76-b101-affd50e0d861
15b8fb86-6025-524c-a968-0d8e442c0d8e	97dbf2ef-f2b0-5619-a13c-aae71b57a930	04d388eb-ebe2-59f5-a595-0a49fd2ba6b1	14d61063-55f9-597c-8d90-a7cae6788659	\N	04d388eb-ebe2-59f5-a595-0a49fd2ba6b1
ee82d23f-991e-59ba-9646-d407d35b46ae	19c76a67-ef88-50a7-8bf8-7bdf4848ed8f	d8b591b9-6712-5bac-877d-a0ea7a35ab02	b37e587e-31a3-51fe-99d1-83b0269a2a04	\N	d8b591b9-6712-5bac-877d-a0ea7a35ab02
f41b3958-7c0c-5c05-9ea8-d0099b014f0c	d04fad4f-88a6-560e-b256-26700f659d4b	06962e25-3684-5a8b-8ec8-4df09922b020	505c4b7f-5afe-5f48-bc87-02970eed49f6	\N	06962e25-3684-5a8b-8ec8-4df09922b020
0d91d253-e394-572c-a6c4-37e20ed0f603	0e9f0eec-41ce-57da-bbf4-62d7070cbfaf	9737618f-ec2f-5332-b82d-6d159da9bf45	fa6838d5-7433-559c-8a54-d209a7adcbc8	\N	9737618f-ec2f-5332-b82d-6d159da9bf45
a9d318d5-97e8-5990-916c-fd300d951e2c	ccdc24c5-50d4-5ad9-9623-53232651beec	704f5431-5607-519a-9e09-14bb15c23829	9f194084-f37c-5782-8d8e-3479286f065a	\N	704f5431-5607-519a-9e09-14bb15c23829
45983eaf-745d-5a47-bd34-aa09444736a4	55ad419b-1ed6-5857-997a-370565e9f4bc	03ddbe7d-3d2e-5974-ad4a-2a87227e2822	7446be24-2622-5886-ab93-bd61ced50f18	\N	03ddbe7d-3d2e-5974-ad4a-2a87227e2822
b01b2de0-f80a-587a-99bb-8e7e2157feec	9933d252-41f8-5aeb-a630-abd529aead58	e261a558-0012-5d0c-88a5-48ebbb737894	6b8bb68a-01e9-59cb-8da5-040b6c62fe63	\N	e261a558-0012-5d0c-88a5-48ebbb737894
a2bb0866-9ce3-5b9a-bc60-49b48a40e40f	4850dc97-dea9-508f-a8db-e078f2de63e2	2a2597da-a15a-53b3-a405-5695abfb8878	ddd96c7a-994f-5a38-8df3-2a02a02dcef3	\N	2a2597da-a15a-53b3-a405-5695abfb8878
c511ec54-4f00-5426-89ef-ee1558ccfb60	c9aefe9f-c036-59ca-9274-44470cfc96a3	98a23c10-bc4c-59bb-a8a0-a2f045f26cd6	e50eb0fa-f335-530a-a6b2-62eda61703e7	\N	98a23c10-bc4c-59bb-a8a0-a2f045f26cd6
7d3f6d4b-fd98-5a19-ba56-b7c707092305	98b29dec-0594-5261-9b67-bb1a55744a5b	9901e691-b5b7-5d8e-9fd7-6b5e3979838e	4046a954-51f1-547c-bb1d-468bedf58c2e	\N	9901e691-b5b7-5d8e-9fd7-6b5e3979838e
98e48ea2-8566-55a1-bfb8-d5aaf1a060d0	87b4cacb-b739-5dee-afdb-b869bbed05d8	c628260d-5be1-5d42-90b9-2768cd7babfc	ac373c9e-df68-56f3-9fa3-1e6b55f8e269	\N	c628260d-5be1-5d42-90b9-2768cd7babfc
902b7ccb-091c-502b-a413-5a3a38c655a4	1674f170-0195-5415-8f5f-cd97b6fd1cf3	61e38cd1-2056-5ac6-8677-65588bdd2931	3ba95a23-60ff-55db-9ba1-efd20c78aff7	\N	61e38cd1-2056-5ac6-8677-65588bdd2931
f8d03abe-255a-5717-811a-91f511c39759	35f50c3a-d8dc-55d4-9ee8-65b49a90224d	d53d0bf9-593c-58a9-b66e-81fcb6d099b6	fcfaa861-5338-5679-99f5-48dcbe985ffe	\N	d53d0bf9-593c-58a9-b66e-81fcb6d099b6
d7a063c8-998c-50a3-825c-86f896a5a0c7	d36f1c25-62a1-5648-bd49-083bc58ee7f8	f89b954c-4d91-510c-b4bb-181272ebf76e	f89b954c-4d91-510c-b4bb-181272ebf76e	\N	f89b954c-4d91-510c-b4bb-181272ebf76e
e09e6607-0754-53e7-ac90-7d5eec6a3442	e13f5421-e03c-56df-8fae-fc7176d91261	bf7368c4-7523-5f4a-a5b8-feefb78ce120	08a0c72f-4022-5482-b122-984c5e11feb3	\N	bf7368c4-7523-5f4a-a5b8-feefb78ce120
942ce296-4fdd-5a7d-9c2c-53d4c2ae2de7	dca971b5-63ee-5942-8df6-44ecc006d6e7	a9c6299d-42b6-5930-a6b7-017168a82325	e4ff269d-20d1-572c-b2f3-46570862024e	\N	a9c6299d-42b6-5930-a6b7-017168a82325
d4387364-3f1c-51be-9762-ddf41f844fb7	368114ad-1c0f-5925-bcee-ff97cecd6343	26b801ba-c20b-5d4c-ab1f-a198b60095fb	4c36db95-5c99-5c28-95ce-7c5efc534704	\N	26b801ba-c20b-5d4c-ab1f-a198b60095fb
acc5e753-294b-571f-9ee7-a7cd6eeea235	a086d90c-90c8-5a73-b5b7-65821a44bc94	159b5cf4-b222-5de7-bec6-ddea8f6e129a	6e34b4d0-688d-5a5f-ac40-5e3b66767f11	\N	159b5cf4-b222-5de7-bec6-ddea8f6e129a
93eaf294-a292-5980-b17e-de43755335c9	393c8163-bc02-577c-a142-2f53a1f0486d	3b4881e5-3e73-5ad1-8243-899a56ea4ea5	c8349837-0066-5f4e-9fc1-af46934647ed	\N	3b4881e5-3e73-5ad1-8243-899a56ea4ea5
f5fce96d-2982-5d7a-b7ce-9dc8914c6ab3	54364495-2043-5b80-a659-7ec91944c8f9	974bf955-005b-53b4-9789-55beebb954e5	974bf955-005b-53b4-9789-55beebb954e5	\N	704b6cd4-fb3b-5d9e-b4cf-e63dd0c99ebb
10a5183d-9b5d-58c7-885f-0210e56c1dca	06d21d03-043f-5c07-a95f-1079c2b3aae3	233a127c-3faa-5b59-b566-960b167a2899	233a127c-3faa-5b59-b566-960b167a2899	\N	c0560764-63de-5057-80e9-e00544932f93
f7aa023d-a413-562b-bf45-75367462a4b2	f201b28f-1b00-54f3-b0cd-98a42d68c776	34e6a894-7b53-5396-8845-8ca64029b2ff	34e6a894-7b53-5396-8845-8ca64029b2ff	\N	34e6a894-7b53-5396-8845-8ca64029b2ff
81b8f353-cd1c-53e4-a0f4-1630c490c959	ba766475-bbe4-595f-9268-b4a791a41404	4a87d97c-dc60-5885-9422-64a4b4dca58a	4a87d97c-dc60-5885-9422-64a4b4dca58a	\N	a80baa57-ca13-51b3-a2ef-b4b5cc7a8858
e2c0e2f3-891a-55e3-ad28-285669d27287	2e8b8244-4040-578f-9259-c08f47114166	a5c6cf98-c919-5800-8116-fd22541b26e8	4dbe0df7-565d-5d4e-a9c8-a072e496707b	\N	a5c6cf98-c919-5800-8116-fd22541b26e8
2dd4acb2-11ca-50c5-b365-c22616ea316d	bcc9b737-1efb-5df9-99bc-6ac1e188ecf2	ee5f5c8b-28e4-5b29-adc9-54743cee511b	ee5f5c8b-28e4-5b29-adc9-54743cee511b	\N	ee5f5c8b-28e4-5b29-adc9-54743cee511b
496222cc-1cb3-50c8-9e7e-bbc7d327b78c	a6cef35b-53cd-5aff-bb87-a147a3cf35fa	0db87bcf-5537-579b-8632-e5e07110ff5a	b5c1b15c-a3d6-5dbf-ba4e-39ce8ed1007e	\N	0db87bcf-5537-579b-8632-e5e07110ff5a
4fbc0a01-e32a-5449-8efe-3ae37aee4f2f	bfd18a6a-40c7-5f0b-8307-30a763e0794f	76b72784-202e-5c9d-bc12-d0e39e7ed1ff	59a4fea6-3b71-5e21-ae10-1af3386fb32e	\N	76b72784-202e-5c9d-bc12-d0e39e7ed1ff
5e2b2d0d-7461-56e2-a151-9a10375d5615	b2abf7cc-ffcd-5c82-afac-83ba81b65921	cf3f76da-4fec-54f8-b6ce-5aa132a99f25	3ee76ed2-f30e-5f35-930e-8545f1212b53	\N	cf3f76da-4fec-54f8-b6ce-5aa132a99f25
12c11165-7996-5b13-bf19-c3afc419afb2	0ab63e1b-e014-5c3e-865c-3debeab8f07c	8a216bf1-3249-55a0-baf7-1d0244a750a5	8a216bf1-3249-55a0-baf7-1d0244a750a5	\N	97c79d15-8610-5884-864f-c3050730ffb0
b1852be8-7a0e-5541-b94d-29be625cb423	f01ee2b6-5f41-5f92-bd38-432d8ec72cc2	593ec946-041d-5815-8e32-02c86f1ab10c	195aaa68-b2db-59a4-bfb5-f865a7698857	\N	593ec946-041d-5815-8e32-02c86f1ab10c
b5d661b7-f275-5647-a86b-cf4bd963f512	1d943538-3c44-5acc-9534-33e5e8ff737b	6af3c638-ed95-58fa-a1cd-8a21db67641f	6af3c638-ed95-58fa-a1cd-8a21db67641f	\N	5c3e9023-77bc-5efd-9027-c1c964f345aa
96fb26f0-20df-51e0-b1c4-793ab736c2d9	7af83019-171e-5618-ae0d-c2feb1fa0f92	34784e82-6341-5dc5-b4a6-db5ddbe97faa	9a413add-7cb1-56da-9243-5b7a647098aa	\N	34784e82-6341-5dc5-b4a6-db5ddbe97faa
b802b5a4-5804-5f5a-a193-e475bceaf5dd	ddfd0720-cf03-5c8b-9eb4-a3aeb1ce9ff1	b1d59157-8068-5154-ac3f-c35178461371	afa50ef6-3c71-54ba-8201-ec016fe4c7ab	\N	b1d59157-8068-5154-ac3f-c35178461371
04bddd51-0749-5efe-9946-6d326f61bcb6	7048d6ad-bcab-59ec-ad20-b822ed218420	7d8b3a4d-61fa-59a2-ab9a-fa87fe0bc821	25bfe68a-7602-5388-98b1-5701dd8717f8	\N	7d8b3a4d-61fa-59a2-ab9a-fa87fe0bc821
f8a3e40d-f42e-5ba2-995d-3abc33e63a41	62a33245-0317-54bc-955b-fd9e155042bc	37b05dda-b3db-5c66-a394-6c89059dc6da	fd2a4110-3c48-5a39-81e1-074eec67a75d	\N	37b05dda-b3db-5c66-a394-6c89059dc6da
1c5e45ac-cc4a-5d53-b566-d6d832828ba0	8abe6eb7-564e-5d16-b7fc-637ab4ce4679	ab3b6710-c8fe-52ab-b814-1cfe807989e3	ab3b6710-c8fe-52ab-b814-1cfe807989e3	\N	ab3b6710-c8fe-52ab-b814-1cfe807989e3
5f6cc5c3-6625-5caa-9277-f43493360fc2	7ccdef60-7734-57c3-bc38-b7b144b90ab0	5e0736d2-2730-53b9-98e4-5dd5a7533ece	0c2a2e9e-9d89-5418-bbf1-7374dca12535	\N	5e0736d2-2730-53b9-98e4-5dd5a7533ece
6d055f49-622a-5d8a-a7d2-11de3279b9ce	2951c376-3600-57e2-8ede-fe37173e420e	112520c5-b908-554b-8d1a-ad6a989105bb	ad4d30b3-7fd0-5e08-974e-8aeaa4c73d35	\N	112520c5-b908-554b-8d1a-ad6a989105bb
3a52dad1-de68-523b-b6ad-3148496bcf8e	1b01ab3a-2e1c-5ef7-93fe-e4e18addc4a2	80ef54f2-c504-5795-92bc-106b0837855f	58cc22c2-b05b-591b-9bf2-806daa240e3d	\N	80ef54f2-c504-5795-92bc-106b0837855f
dd52de46-6d78-5bc3-96ab-b25e9be9e291	dff1996d-452e-530d-af3e-36186de7ac75	0372f56e-6b3b-55f5-91f6-bea000e24fca	663d3405-daa4-5b6e-9707-3b9d7f90c2a9	\N	0372f56e-6b3b-55f5-91f6-bea000e24fca
89b553be-905d-57aa-946c-86dc9f366aa2	096c68b1-06f4-5f60-b323-c1803c3e8633	396a3e6c-045a-5e7f-ab9c-d0211e616070	7b1755bf-7f10-5127-b55c-7fbe538445d0	\N	396a3e6c-045a-5e7f-ab9c-d0211e616070
f7d4fade-03e1-53e3-94c0-24034e6a9e08	9c4d029b-134c-5412-b8c4-9ae1c5a0c032	13f0c34e-6b90-5fdf-b8fd-ac54f5e8c785	13f0c34e-6b90-5fdf-b8fd-ac54f5e8c785	\N	6e827086-043d-5ef1-914d-28f1ff4c4740
fbae6594-96c7-53f0-a80b-bd325d5a22ff	b5600c12-3aa8-567c-938b-e7a8d407779e	4b5063b7-4536-581f-ab42-a6aa0671bede	403665f7-94e6-5cfa-b267-55a517aff2b9	\N	4b5063b7-4536-581f-ab42-a6aa0671bede
dcb08e4f-eb7c-5536-bd57-ee51127a14be	d5cc3edc-2d1f-5407-9971-8e8689185f45	0ab19f69-3584-5bee-b551-97f796a4ffaa	7d97e7ed-05f9-577f-a87a-0e1dacc834ae	\N	0ab19f69-3584-5bee-b551-97f796a4ffaa
845d24c6-6c7c-50f4-8dd7-450485839fa5	c2cc6878-a61c-521f-ae40-73dee64c8640	326f6a64-a341-586a-ac47-08a8353b1b18	66739f6a-e449-5698-9fe0-114cd8d9f8ab	\N	326f6a64-a341-586a-ac47-08a8353b1b18
c007b505-323f-5517-8353-ed093d1571de	25f88057-2ae2-5cfb-976e-8bf7a89344fd	f8c15ef0-c171-59ef-9e7b-7cbceb015969	714bcad8-b14f-5f85-b9d8-64fd3ab5c786	\N	f8c15ef0-c171-59ef-9e7b-7cbceb015969
8873834d-7358-505d-a93b-7782dbd00024	4ee83cd9-765e-598b-9b23-a553a49ccc62	a12148a7-c033-5afa-85f5-a3cb5f665512	4b9c0c09-3acd-56a1-8c54-271f16ad0212	\N	a12148a7-c033-5afa-85f5-a3cb5f665512
0f9e7c25-4cfa-5186-a893-0f59b7e34745	ccccdb55-dd94-5e0b-84b3-e5f9aea3b4c3	12ad3482-9179-5312-bb91-e49fc9499612	afe3e46a-e7e1-5c87-8a31-e5d0355dd42e	\N	12ad3482-9179-5312-bb91-e49fc9499612
e469a7d8-cfad-5a85-ba5c-cb1d0c8ca1b0	e5e5ace6-768d-564f-bcde-087269bfe38e	2491792c-5ac1-5fc8-95e7-86ea5fda6259	2491792c-5ac1-5fc8-95e7-86ea5fda6259	\N	2491792c-5ac1-5fc8-95e7-86ea5fda6259
6d9b3812-644e-5890-94cc-09a2fd01a30a	66ccddbc-7a6b-5b27-bb84-904067404aff	40c4ab0c-9f4a-5200-9bd2-dca06422c461	331456ae-1d14-5a3d-baf1-7a667bb20821	\N	40c4ab0c-9f4a-5200-9bd2-dca06422c461
aaf16bcd-1ecc-5f72-97a6-497825719aba	4dafde42-f51c-5bd6-be5c-31ff34b3d171	35175425-22ec-5ab5-9180-3eb5e9442734	c5a1fded-3782-57c2-989a-f0bfb84ea40c	\N	35175425-22ec-5ab5-9180-3eb5e9442734
e05ed6a4-edcb-5052-9760-1e10c70c2236	7477317f-05dd-52d1-bf47-9a9a6ae21944	31ff8e41-0414-5d3b-b96f-66b39686b2d7	dea1d1b7-1ea2-5afa-90e0-7676713a7f9d	\N	31ff8e41-0414-5d3b-b96f-66b39686b2d7
0f06fb63-c508-57a2-8983-2cb9f5dfd2e7	52448f2c-43b4-5bef-8950-4fc15921eea2	8b1b7f5d-b982-55e7-9de7-4a9da5c8a8d7	d018bbde-937e-5e7b-aed7-98080dc593f9	\N	8b1b7f5d-b982-55e7-9de7-4a9da5c8a8d7
662aaf34-2fd3-5ab7-ad40-ee6e4862fc2c	99cfad43-cd82-5fd6-b14d-4937691bc6a7	fd2b4776-fa1e-515d-b945-91e977f7299e	ffbebe7c-10ed-5531-93d4-80265c2ae072	\N	fd2b4776-fa1e-515d-b945-91e977f7299e
06eedaf7-2c40-54e4-a1ea-669b265fda8d	546365b8-30f9-5ac8-9615-3f2f497fec2b	a44ee792-e394-5804-8707-386592aeb81b	fceb9b86-3f97-5f04-b522-b533ea61cbbd	\N	a44ee792-e394-5804-8707-386592aeb81b
b82fa253-fbb7-559c-b127-77d2262df800	ada1d499-ecea-51fc-b8a9-b365e7e312ca	ae3a4b03-6b24-5e39-afe0-6a94f152cdd8	4c808a04-86a9-5e18-95fc-6458a29d5e3b	\N	ae3a4b03-6b24-5e39-afe0-6a94f152cdd8
aa05a718-c2a5-5083-9c3d-b0e4d1bd09a8	0da32afd-48f5-5436-a3e0-1b8b54e265bc	9e315722-56b7-5a67-9be1-213fa5c90370	be93cd6e-647a-56a3-8a7d-ca9edf0c9bc5	\N	9e315722-56b7-5a67-9be1-213fa5c90370
3bef2c81-886b-5bbd-9f1b-dfc94288bea2	d1713f19-aaaa-56f7-852a-3978de3b793b	7bda97f5-7b2c-5356-b54e-792d06ecca63	a262a3f6-c2e5-542e-81cf-b2801d2e3a19	\N	7bda97f5-7b2c-5356-b54e-792d06ecca63
dbf81e7a-c846-5129-b017-a31ad90eb752	f20ebb6e-5253-5ae1-8d7d-30f9c7fff09f	ef2f1e4a-366a-5f14-a6af-c6e6a502181b	27b33027-745c-55df-a82d-401f64d84976	\N	ef2f1e4a-366a-5f14-a6af-c6e6a502181b
4b6153f0-1c2e-5330-94dc-e71afa17d386	d577df4d-e545-5f7e-8ac8-691779895c6d	d86c9f10-796e-53aa-897a-9299e71f95e0	186d373a-af22-5e74-ab8d-bfa5dc640366	\N	d86c9f10-796e-53aa-897a-9299e71f95e0
ff69f94e-49fa-5015-a8e8-6bdab429f087	6a88fee3-bbb5-5ef6-a79c-b28c0e7d6be8	1c628527-0ac5-5e63-8b2d-76d93e27ecb1	d59f0854-f076-5966-bb98-0fb14f7d18a5	\N	1c628527-0ac5-5e63-8b2d-76d93e27ecb1
455c3d21-e4c5-5cae-a4b1-2f4a0b988e50	5a5ef3d4-97d0-559d-99a6-87fd6a29035a	cf081311-5a4c-548e-b917-e4921e8a5b6f	7406b2c1-bea9-513c-9140-87823395b10e	\N	cf081311-5a4c-548e-b917-e4921e8a5b6f
43903683-a322-530c-88e1-a1c406296be6	6bd06588-251f-581a-ac11-057711cfe513	480494bb-7ade-5ed2-b086-62456da3bfed	da647682-f600-5815-93b4-d006ca201e96	\N	480494bb-7ade-5ed2-b086-62456da3bfed
4e1fc6f4-772d-5a1f-8391-5453bcbf4817	3c93761c-c9d9-5ea5-abb1-2fc4ff69a450	caa2b9cb-99f6-532f-b576-e192e9bbb901	c6e4670c-d1fd-57a9-9672-f0bbc53f3245	\N	caa2b9cb-99f6-532f-b576-e192e9bbb901
e6aa13b6-6f6a-57db-8433-701ba55fa918	063882d2-f6a4-5927-8d33-7fe0a94a6f1b	f04da983-97fa-572a-afb4-274d6295a907	b4f062fe-d959-5334-9aab-c185955f0bc9	\N	f04da983-97fa-572a-afb4-274d6295a907
522046f3-2cfa-565e-a8d5-15f048b49776	595879f4-7bff-5ffc-8650-ae37b7f89bd9	2df7adb4-7be3-54f1-a17e-70d199ab1435	2df7adb4-7be3-54f1-a17e-70d199ab1435	\N	7e57285a-599a-51a8-a3f6-91613f0c8eb1
3972d239-6e9a-5163-bf43-c4a2b81783db	14fe3bca-81a8-598d-ae3b-bd2068ea6081	68904bd3-8910-5e50-af0b-18a5e6e17fde	6f970b2c-2759-51ae-b7d1-fb3831b53d96	\N	68904bd3-8910-5e50-af0b-18a5e6e17fde
a4dbf3ac-a372-58a7-b9ac-a7f018ba587c	d0fc0984-fb21-5b79-a0c6-4b404a1c4cb6	3f0ebf47-15c2-5419-831e-a2d9d2a3d7d7	3b1af04a-9a5e-56a1-8ce7-664f74179aa8	\N	3f0ebf47-15c2-5419-831e-a2d9d2a3d7d7
b415c8ff-d8fb-5e40-9a89-ed5fa1997831	877384c3-9344-5833-9646-ab43ec6e4511	943d1186-49af-5019-aaf7-845aa4a64b66	022cfee4-a9d3-5585-bd68-5a9148004557	\N	943d1186-49af-5019-aaf7-845aa4a64b66
e872c853-3159-56b7-834e-066e364a5782	63e28731-0166-5521-bc93-e8139e4e34d7	c9871de1-bc76-5fc0-aec0-406bd3745afb	8cc981ca-b491-5c68-9a56-18294c5743ac	\N	c9871de1-bc76-5fc0-aec0-406bd3745afb
7ec8469c-ee0f-5a33-9f9f-e025e6cffdd0	f78152c6-9972-5880-8932-4b8e2e0fa661	45b43f20-7c24-5f2b-ab1b-b8dbb4f7356e	a374e696-c3e5-56a9-a24b-455729f9e734	\N	45b43f20-7c24-5f2b-ab1b-b8dbb4f7356e
1efcd811-18f8-5b64-be29-9f46fbef3286	c81519c0-98d0-575e-95ec-dbeb16a1e096	978e7bb1-c35a-5d00-8c7b-5be1235be5fd	732b45e3-1b38-521c-a719-da5599a8710d	\N	978e7bb1-c35a-5d00-8c7b-5be1235be5fd
9e1ccd85-e76b-559b-9c13-ac2202a04849	5486a985-4f94-5b7c-abf8-3b50824d4832	ef94c592-242b-5cab-8195-86144bf828da	ca2ead6a-5c21-569f-9c5f-f2b4fb4d7bc1	\N	ef94c592-242b-5cab-8195-86144bf828da
788e64aa-46ee-574d-aad9-83c8b56bc40a	930211dc-15e1-571e-98ba-fc458ea76bbc	a5284a6a-7a00-5315-b6ff-6e4c162df689	502dcd13-6ace-54d3-b94d-0cf6581ffdac	\N	a5284a6a-7a00-5315-b6ff-6e4c162df689
f7d20fe3-57b3-5e6d-85f6-5b54b0ba7088	7396ce96-d427-5d3b-9aee-bdea76136921	b7d10589-eb26-5aa7-9414-f67ec5c70a70	b7d10589-eb26-5aa7-9414-f67ec5c70a70	\N	b7d10589-eb26-5aa7-9414-f67ec5c70a70
61637bb4-cc4a-52be-86fa-7fb678ab6e62	4e1a4700-9373-564b-ad1f-d8f771d2e856	13c8c94b-4e46-580a-8f5e-f0320a39b319	c0954054-5887-5265-96b1-cd445bad7358	\N	13c8c94b-4e46-580a-8f5e-f0320a39b319
50cbf96a-57d3-5f45-b5fc-e415ff57717a	c4fb4058-794e-51e6-a5af-e6443b831b1c	fae34bc4-96e0-5534-bfc0-d4e59766cff8	6b9521d3-8f71-5429-b4e2-5ddbcfde5313	\N	fae34bc4-96e0-5534-bfc0-d4e59766cff8
243bdf44-b101-5d5d-bc51-a4f5c1c03bc3	68e0bbda-553f-5f5a-b3bf-7d13505974ca	48eb2d04-e25e-5ec9-825f-19ea38803db1	c1766267-1a80-5742-bb98-d2ae8515e448	\N	48eb2d04-e25e-5ec9-825f-19ea38803db1
27c14160-d0d6-51f8-b7b1-9172cf8b84d2	d4a9bc61-0e45-5a98-9ae6-b60e33922a75	669a71d0-7288-5f83-b66d-ba9c40e2188f	ed04ab7a-ebf5-5b32-8039-80f0724eacb8	\N	669a71d0-7288-5f83-b66d-ba9c40e2188f
b8a9c502-6f50-5d50-96f1-417feef69161	81eaada1-9e11-5bea-b007-272bba015494	f228a714-c3b3-553e-87c6-ebc85e40c6ba	c96dfbe4-4eac-55d9-8106-096eab5a26cc	\N	f228a714-c3b3-553e-87c6-ebc85e40c6ba
87e72b5b-0332-5b61-b21c-ea8cc6a05c1f	649204e1-57b4-542a-92c9-7b2f9fbb2d0c	2e51a478-a7e4-554e-b12a-16f37d55a888	17e1d10b-47f4-5929-be2e-da136c537152	\N	2e51a478-a7e4-554e-b12a-16f37d55a888
615aadf7-6900-5c48-814a-d6cc0884ae86	8ed82071-7787-5904-ab49-6847157cb1af	5c4eedcf-4c9c-548c-a00c-de9261275d44	359d7060-7f35-5f8f-acf2-476fd99adb02	\N	5c4eedcf-4c9c-548c-a00c-de9261275d44
bd0e8ffa-f7cd-5b27-8481-6ecb87512f6c	0ec19188-81f5-5a1c-91c5-d12e747c3f31	9938b5a9-495e-5825-a10c-fa2848dc9fe7	499c4e39-aa7c-59e4-a742-4bc752975f2e	\N	9938b5a9-495e-5825-a10c-fa2848dc9fe7
28166f6b-3aba-5230-9bd4-18e1a467bc3f	82a14680-5546-5c0b-a995-89982701d01b	bc763c1e-d3ba-520d-a994-d8dbd8a878a3	d6f65dc3-3834-57af-9912-cdf8928904fd	\N	bc763c1e-d3ba-520d-a994-d8dbd8a878a3
45b4593b-f7a0-516e-84c4-68c7a7099243	c9602780-2bd3-5de7-9404-f31e4257c6bb	a44f4e44-20c5-5c46-ab3a-c78352c643f6	5921a394-3b72-55da-a798-efde8d70ba24	\N	a44f4e44-20c5-5c46-ab3a-c78352c643f6
66e4cf05-72cd-512b-a462-05f4e69906b5	5010b467-349b-5de4-b53a-c715f3c54f47	ad9d8a4a-5776-5918-9cd7-ab617ded64c8	c17eaf30-53db-518e-86d6-e91d7702933d	\N	ad9d8a4a-5776-5918-9cd7-ab617ded64c8
10567940-e3bb-5257-b33c-ae9976bfaddd	60ba9d85-6042-552a-850f-b3524808c3ef	bdd8a736-404d-5b67-9dcc-be354e0725e5	81c35e68-258b-52fe-b85c-76be5dd16fc1	\N	bdd8a736-404d-5b67-9dcc-be354e0725e5
9e9dc42e-df1e-5ace-948a-9dce72caaceb	6b9c8ffe-cdbc-5845-9e19-b0f923ac3467	0023dc73-8fa0-58aa-8fba-2bd1bcf343fe	b222cdbd-182c-5b8e-b00c-93e2bcaf492e	\N	0023dc73-8fa0-58aa-8fba-2bd1bcf343fe
fc5427a3-a54b-5c04-8b36-a570b8e7a72b	10c983d4-5fb3-59c7-8529-ba34827861bb	daa72300-c1e3-50c7-aa04-fd0ae580966d	343b9649-76d5-5277-b024-a71f21e10de9	\N	daa72300-c1e3-50c7-aa04-fd0ae580966d
1e68ea18-9d69-5761-a463-510eab20d1d3	74ebb7c6-2940-553c-872c-f139f5f43f00	aa2b1deb-a8b5-55c4-884b-496235ad9819	42eb00c6-5af8-516c-90ee-a3f7a2134d57	\N	aa2b1deb-a8b5-55c4-884b-496235ad9819
818210e0-641a-56f3-9241-7d24504480e2	e1766190-9a78-5b43-9122-9aba92664389	95963f3b-74ec-578b-9077-2c457fdef5d1	95963f3b-74ec-578b-9077-2c457fdef5d1	\N	3cdcdc20-a6bf-5a81-9962-5d15ab450859
02115b33-cd19-587a-a3f2-446dea254e33	a6002c5b-4c7c-5e5a-a59e-44134bcd0078	64f9e91f-e687-5062-b259-bcf29d476066	69f55c60-055a-545d-9f80-aca4f5e151c8	\N	64f9e91f-e687-5062-b259-bcf29d476066
f5f775ad-523f-5888-b269-fcb13c1c1051	62adce6f-af9c-5fe9-baf4-1a12fb34c61c	f80185d3-81be-51ea-96ef-583303d320e5	44569812-2095-5367-8e63-16b0f744a24c	\N	f80185d3-81be-51ea-96ef-583303d320e5
6ae314ed-22ae-5a5f-8e43-c5165e6eb3a1	aaaa1d00-a3e4-59e9-b6ea-b0fdd6c7ebd3	9444575e-54af-5498-b338-1d0a757e5646	6e3adf8f-f6c5-5b52-a838-b75406838c9d	\N	9444575e-54af-5498-b338-1d0a757e5646
8640d0ab-1efc-5b18-a49c-564f8e53d65c	8d6806d2-796c-5bdb-9862-48f2810aecdd	67008121-6a9d-520f-a4b1-a64d08a2f9fc	070d5515-2b7c-5dc3-8a51-353fcd4a887c	\N	67008121-6a9d-520f-a4b1-a64d08a2f9fc
\.


--
-- Data for Name: reference; Type: TABLE DATA; Schema: vocabulary; Owner: postgres
--

COPY vocabulary.reference (vocabulary_id, lesson_code) FROM stdin;
f552ea3a-8b7f-5433-aa20-4c93a6868fb3	GENKI3_L00
8359d7d1-d6d6-5995-962e-3f6566232911	GENKI3_L00
4c55fd59-1160-52d2-8383-00f292668c1f	GENKI3_L00
4c55fd59-1160-52d2-8383-00f292668c1f	DUO_01_02
8d46e404-38ff-518c-a25d-cb95d6f2c511	GENKI3_L00
1184380a-27db-5f8e-841d-7b105c2a7366	GENKI3_L00
1184380a-27db-5f8e-841d-7b105c2a7366	DUO_01_02
3ecfb07b-0a66-555c-97c4-5af7cb806507	GENKI3_L00
a419bb62-f061-5ac6-856b-6f85002642c6	GENKI3_L00
c7a645f7-256a-536a-a5ee-5f85e8ce1137	GENKI3_L00
b626308d-6418-5a2c-9151-7a8a368f27a3	GENKI3_L00
1913dbc6-4712-549a-9274-dc3511cecbfe	GENKI3_L00
e7161fcf-fbaf-5971-8adb-70745ae24103	GENKI3_L00
c2a4dbcf-dba1-5e27-964f-5e2f7b358725	GENKI3_L00
b06abb09-c9bd-5beb-a7b7-d184bedf72ca	GENKI3_L00
47b08527-885b-50bb-b538-180dc308199a	GENKI3_L00
797b03e3-1495-52c0-baf4-ffb9cb324089	GENKI3_L00
59bad243-8b31-54f3-9d7b-6de0961bc059	GENKI3_L00
05436324-1858-5a2f-a2a1-a41d00f862cc	GENKI3_L00
136d6d6d-0487-5709-ac27-2c376bfebd43	GENKI3_L00
ddd5fd1f-f041-576c-a479-d8d9a3221370	GENKI3_L00
d43cc787-37af-5dd1-92b5-fc756f18298a	GENKI3_L01
d43cc787-37af-5dd1-92b5-fc756f18298a	DUO_02_05
3c890a54-15f9-56e1-9bd5-3c91274b6246	GENKI3_L01
3c890a54-15f9-56e1-9bd5-3c91274b6246	DUO_02_05
6de86986-05ed-58da-a055-9d49c89f7a06	GENKI3_L01
d1cb9e5b-6cc5-5339-b33d-4e948229156e	GENKI3_L01
d1cb9e5b-6cc5-5339-b33d-4e948229156e	DUO_02_05
efe5fea4-0476-5328-bd18-2a469a8b6c3e	GENKI3_L01
efe5fea4-0476-5328-bd18-2a469a8b6c3e	DUO_02_05
c21b42a5-1ef1-51cf-9402-199d2b562a47	GENKI3_L01
057a7cd1-f1a6-50e2-8449-3b6f04c67305	GENKI3_L01
057a7cd1-f1a6-50e2-8449-3b6f04c67305	DUO_02_05
e71aac30-c949-5b47-b118-d02a5d703701	GENKI3_L01
4b1d8b7f-6477-5881-9439-1a7f30b26c9d	GENKI3_L01
4b1d8b7f-6477-5881-9439-1a7f30b26c9d	DUO_01_02
f65b93fb-1086-5405-8163-394db3e83014	GENKI3_L01
c26e8356-f73a-5ddf-b045-432580c37468	GENKI3_L01
0ef6972f-5e7d-5b51-be7f-8f39a5fd5e0e	GENKI3_L01
146b7705-d9cd-511f-988c-5b2cc7661b75	DUO_02_03
8bedfb60-1599-58b0-9568-5e72b8cf6b7e	DUO_02_03
25f93b07-dc04-553a-8794-329cc907dbe5	DUO_02_03
37eba762-70cf-5c5c-b689-621f26c1ee5f	DUO_02_05
99cdf9f9-df8c-5d7a-9ea1-a7c003c9f289	DUO_02_05
947e6f87-e4fe-5b27-9bd3-215e8b1fc71a	DUO_02_05
dd191fac-e29b-5262-b2a6-f9f2875a4a3f	DUO_02_05
fc82f2ac-3b17-582f-9259-7200e1430824	GENKI3_L03
fc82f2ac-3b17-582f-9259-7200e1430824	DUO_02_05
15c5fee6-dda7-5d40-b7fd-382d97a28b9c	DUO_02_05
39458010-a5eb-5b13-b054-4cc458702c56	DUO_02_05
091d6d9c-5d19-51f5-bc28-3b5281042464	DUO_02_05
6e44df4a-4366-5c1a-86af-87a4e5f61fc6	DUO_02_05
7748dd60-2126-5e30-a5b6-39596a0e25e4	DUO_02_05
dce3fe6a-497c-54b2-a121-df6767084258	GENKI3_L01
dce3fe6a-497c-54b2-a121-df6767084258	DUO_02_02
42ffdbae-c27d-53a4-b113-95583953e16c	GENKI3_L01
42ffdbae-c27d-53a4-b113-95583953e16c	DUO_02_03
94611381-0488-58aa-8cf2-12ad9ff4e2dd	GENKI3_L01
94611381-0488-58aa-8cf2-12ad9ff4e2dd	DUO_02_03
40502e18-b4ba-50b4-89b7-23fd3b4d4a21	GENKI3_L01
40502e18-b4ba-50b4-89b7-23fd3b4d4a21	DUO_02_02
5372b023-b803-551c-9a7f-f404804f63f1	GENKI3_L01
5372b023-b803-551c-9a7f-f404804f63f1	DUO_02_02
2cf1d32f-b261-5ba6-829c-a9f1237a88d6	DUO_02_02
a39e794c-9682-5cd5-9c5e-dd961aa92bc5	DUO_02_03
9e67a677-9058-536e-85d6-389ea5138c86	DUO_02_02
252852aa-aefd-504b-b671-668a33020492	DUO_02_03
cc6a4913-6f39-521b-a0cf-9631f640800e	GENKI3_L01
522e0796-297d-5bf2-be85-e93596baa6bf	GENKI3_L01
a420a37b-593e-572e-aae7-d724eca6803d	GENKI3_L01
c59169cb-e5ad-5311-a9ce-43764a5ddda6	GENKI3_L01
1731f988-dcfb-5b31-bda9-c8988a5711bf	GENKI3_L01
f6524cfb-fa66-56ae-ae4c-0e38a8b6e54c	GENKI3_L01
d8f98686-282e-5c5f-88b2-4e046e707e54	GENKI3_L01
728f5a87-3848-5450-a941-f9cd0f898067	GENKI3_L01
507092ff-6dc3-5749-a9c3-79ba8d2bc360	GENKI3_L01
84e857b9-5605-51fb-bfb3-8cc08eb579d3	GENKI3_L01
dde2be42-c934-53fc-9dc6-3ce12b727924	GENKI3_L01
0428342a-6b57-5e6f-b1c6-4aa6ccfdb267	GENKI3_L01
a48cfff2-551b-5525-bf29-f706571e7e15	GENKI3_L01
311cf3c0-5798-551b-a933-eb33c4ce7843	GENKI3_L01
40483fe8-7fda-56b7-b28b-eedb9da50a52	GENKI3_L01
2a0ff39c-471b-56d1-a2b2-39f20596f70c	GENKI3_L01
5cb08712-5c04-56f0-aaf6-a8e76a1e0ef4	GENKI3_L01
b4a146e5-f483-50d5-8b18-ca71fdb3f570	GENKI3_L01
54ca6cf2-c9f0-5a77-b53c-8e4a2fbe76ac	GENKI3_L01
f3f3ba33-de3b-5fa1-9b99-eee62ae2e024	GENKI3_L01
eb20360e-5c1a-58b8-9bf8-040795c5bf0a	GENKI3_L01
ae144a7c-293f-5f24-81ee-37d5594d6aed	GENKI3_L01
abd1e50d-f284-5a21-831f-344cb338414e	GENKI3_L01
13912d04-d947-5c72-a192-740c19a28c86	GENKI3_L01
7672ef18-d91f-548f-a938-970d6a741586	GENKI3_L01
471eeab4-7852-53f9-ba6c-342f13746958	GENKI3_L01
9e62feab-62dc-5faf-92d9-8cd423af7dab	GENKI3_L01
15b8fb86-6025-524c-a968-0d8e442c0d8e	GENKI3_L01
ee82d23f-991e-59ba-9646-d407d35b46ae	GENKI3_L01
a74f0164-da3d-59dc-bbd0-39cf065ee813	GENKI3_L01
f41b3958-7c0c-5c05-9ea8-d0099b014f0c	GENKI3_L01
0d91d253-e394-572c-a6c4-37e20ed0f603	GENKI3_L01
a9d318d5-97e8-5990-916c-fd300d951e2c	GENKI3_L01
45983eaf-745d-5a47-bd34-aa09444736a4	GENKI3_L01
b01b2de0-f80a-587a-99bb-8e7e2157feec	GENKI3_L01
a2bb0866-9ce3-5b9a-bc60-49b48a40e40f	GENKI3_L01
c511ec54-4f00-5426-89ef-ee1558ccfb60	GENKI3_L01
7d3f6d4b-fd98-5a19-ba56-b7c707092305	GENKI3_L01
98e48ea2-8566-55a1-bfb8-d5aaf1a060d0	GENKI3_L01
902b7ccb-091c-502b-a413-5a3a38c655a4	GENKI3_L01
f8d03abe-255a-5717-811a-91f511c39759	GENKI3_L01
d7a063c8-998c-50a3-825c-86f896a5a0c7	GENKI3_L01
e09e6607-0754-53e7-ac90-7d5eec6a3442	GENKI3_L01
942ce296-4fdd-5a7d-9c2c-53d4c2ae2de7	GENKI3_L01
d4387364-3f1c-51be-9762-ddf41f844fb7	GENKI3_L01
acc5e753-294b-571f-9ee7-a7cd6eeea235	GENKI3_L01
58ecba76-692b-5baf-a096-62bf3e752da2	GENKI3_L02
58ecba76-692b-5baf-a096-62bf3e752da2	DUO_02_01
8373ee97-fb02-5fa4-9f3a-e6d0bfe42042	GENKI3_L02
8373ee97-fb02-5fa4-9f3a-e6d0bfe42042	DUO_02_01
e3286acb-a31b-5fb6-9c16-28ac16fcc269	GENKI3_L02
e3286acb-a31b-5fb6-9c16-28ac16fcc269	DUO_02_01
9ef8e062-db86-5059-82be-ccc85580d5e7	GENKI3_L02
9ef8e062-db86-5059-82be-ccc85580d5e7	DUO_02_01
430b4ea9-6092-52c7-b2bf-4300de6196c7	GENKI3_L02
9d9f54be-2a19-5147-8935-e7c3c887fa03	GENKI3_L02
908efe1a-b6e9-55d5-b550-461cea175af4	GENKI3_L02
a9b7cffc-2d14-58ae-818b-3ac3b6e6e4d0	GENKI3_L02
9c591bfa-c4a7-5263-b2b1-7c8d14e10c34	GENKI3_L02
0279527b-c398-5856-aad3-11b834907f56	GENKI3_L02
5587745e-e524-5159-8fe6-1e8637b90455	GENKI3_L02
1502a2cb-059c-5a9f-baab-af68a0d09ac8	GENKI3_L02
93eaf294-a292-5980-b17e-de43755335c9	GENKI3_L02
f5fce96d-2982-5d7a-b7ce-9dc8914c6ab3	GENKI3_L02
10a5183d-9b5d-58c7-885f-0210e56c1dca	GENKI3_L02
f7aa023d-a413-562b-bf45-75367462a4b2	DUO_02_01
81b8f353-cd1c-53e4-a0f4-1630c490c959	GENKI3_L02
e2c0e2f3-891a-55e3-ad28-285669d27287	GENKI3_L02
2dd4acb2-11ca-50c5-b365-c22616ea316d	GENKI3_L02
2dd4acb2-11ca-50c5-b365-c22616ea316d	DUO_02_01
3ab19c55-4c6a-53e0-afce-41968363d92a	GENKI3_L02
496222cc-1cb3-50c8-9e7e-bbc7d327b78c	GENKI3_L02
496222cc-1cb3-50c8-9e7e-bbc7d327b78c	DUO_02_01
4fbc0a01-e32a-5449-8efe-3ae37aee4f2f	GENKI3_L04
4fbc0a01-e32a-5449-8efe-3ae37aee4f2f	DUO_02_01
b74ca7fa-4622-5a52-b9df-5260878e687b	GENKI3_L04
b74ca7fa-4622-5a52-b9df-5260878e687b	DUO_02_01
5e2b2d0d-7461-56e2-a151-9a10375d5615	DUO_02_06
12c11165-7996-5b13-bf19-c3afc419afb2	DUO_02_06
b1852be8-7a0e-5541-b94d-29be625cb423	GENKI3_L02
b5d661b7-f275-5647-a86b-cf4bd963f512	GENKI3_L02
96fb26f0-20df-51e0-b1c4-793ab736c2d9	GENKI3_L02
b802b5a4-5804-5f5a-a193-e475bceaf5dd	GENKI3_L02
bf5e0762-effe-5d38-8786-503cde98e504	GENKI3_L02
04bddd51-0749-5efe-9946-6d326f61bcb6	GENKI3_L02
f8a3e40d-f42e-5ba2-995d-3abc33e63a41	GENKI3_L02
e7323b54-9fa5-5cb7-8d68-d2ebe9b379ea	GENKI3_L02
1a0585e5-3bd0-5903-90cc-d3381778da68	GENKI3_L02
1c5e45ac-cc4a-5d53-b566-d6d832828ba0	GENKI3_L02
612eac67-3ec3-535e-94a3-249d60f51046	GENKI3_L02
442d1c8e-a848-5fa4-8fc9-d398441b4b65	GENKI3_L02
5f6cc5c3-6625-5caa-9277-f43493360fc2	GENKI3_L02
6d055f49-622a-5d8a-a7d2-11de3279b9ce	GENKI3_L02
3a52dad1-de68-523b-b6ad-3148496bcf8e	GENKI3_L02
6c108fe0-b52d-56de-96f2-f8e0bf450367	GENKI3_L02
14edc8de-8ed3-58f5-a083-13a34db6f490	GENKI3_L02
dd52de46-6d78-5bc3-96ab-b25e9be9e291	GENKI3_L02
89b553be-905d-57aa-946c-86dc9f366aa2	GENKI3_L02
f7d4fade-03e1-53e3-94c0-24034e6a9e08	GENKI3_L02
fbae6594-96c7-53f0-a80b-bd325d5a22ff	GENKI3_L02
dcb08e4f-eb7c-5536-bd57-ee51127a14be	GENKI3_L02
1cee44df-fc33-5279-8d09-5051e714782f	GENKI3_L02
845d24c6-6c7c-50f4-8dd7-450485839fa5	GENKI3_L02
fc6be215-8cf3-55b8-8a6b-0dd345852963	GENKI3_L02
fc6be215-8cf3-55b8-8a6b-0dd345852963	DUO_02_01
3bb29baa-6e40-5c58-93cd-2000ea1120eb	GENKI3_L02
8dc54b41-91cb-5289-bb0b-5f4a5854fea7	GENKI3_L02
5891a34d-ba0a-5458-b42e-f82c60d27f9f	GENKI3_L02
48b8587c-d363-5b54-94d9-c9e5dfff2412	GENKI3_L02
c007b505-323f-5517-8353-ed093d1571de	GENKI3_L02
8873834d-7358-505d-a93b-7782dbd00024	GENKI3_L02
0f9e7c25-4cfa-5186-a893-0f59b7e34745	GENKI3_L02
59d58d35-f0f2-5535-8404-8cb786e2c830	GENKI3_L02
82339058-2123-58a8-93dc-b1c0b05cdae8	GENKI3_L02
e469a7d8-cfad-5a85-ba5c-cb1d0c8ca1b0	GENKI3_L02
49ab0509-4a86-552d-975b-fc49e74790d4	GENKI3_L02
6d9b3812-644e-5890-94cc-09a2fd01a30a	GENKI3_L02
aaf16bcd-1ecc-5f72-97a6-497825719aba	GENKI3_L02
e05ed6a4-edcb-5052-9760-1e10c70c2236	GENKI3_L02
0f06fb63-c508-57a2-8983-2cb9f5dfd2e7	GENKI3_L03
662aaf34-2fd3-5ab7-ad40-ee6e4862fc2c	GENKI3_L03
06eedaf7-2c40-54e4-a1ea-669b265fda8d	GENKI3_L03
b82fa253-fbb7-559c-b127-77d2262df800	GENKI3_L03
aa05a718-c2a5-5083-9c3d-b0e4d1bd09a8	GENKI3_L03
3bef2c81-886b-5bbd-9f1b-dfc94288bea2	GENKI3_L03
bbf5a302-a23c-5142-b721-2aa90ff1e8dc	GENKI3_L03
738002f3-36da-5668-8a8b-46415de8cd52	GENKI3_L03
8f790b97-9843-5efe-b338-e1c37618c616	GENKI3_L03
5ca8a07b-ac34-5789-ab4a-8bd29d51929c	GENKI3_L03
b3e732b3-61c4-574d-8901-b1c74076b5ea	GENKI3_L03
720a22a5-3132-56e6-8380-03a20e2a4af1	GENKI3_L03
dbf81e7a-c846-5129-b017-a31ad90eb752	GENKI3_L03
4b6153f0-1c2e-5330-94dc-e71afa17d386	GENKI3_L03
c675acf7-4ab1-5a0a-beb3-e65aff83932a	GENKI3_L03
ff69f94e-49fa-5015-a8e8-6bdab429f087	GENKI3_L03
455c3d21-e4c5-5cae-a4b1-2f4a0b988e50	GENKI3_L03
43903683-a322-530c-88e1-a1c406296be6	GENKI3_L03
4e1fc6f4-772d-5a1f-8391-5453bcbf4817	GENKI3_L03
e6aa13b6-6f6a-57db-8433-701ba55fa918	GENKI3_L03
e6aa13b6-6f6a-57db-8433-701ba55fa918	DUO_02_04
522046f3-2cfa-565e-a8d5-15f048b49776	GENKI3_L03
c5ebd7d9-06b2-596e-96f5-7366810ea78c	GENKI3_L03
3972d239-6e9a-5163-bf43-c4a2b81783db	GENKI3_L03
a4dbf3ac-a372-58a7-b9ac-a7f018ba587c	GENKI3_L03
b415c8ff-d8fb-5e40-9a89-ed5fa1997831	GENKI3_L03
e872c853-3159-56b7-834e-066e364a5782	GENKI3_L03
7ec8469c-ee0f-5a33-9f9f-e025e6cffdd0	GENKI3_L03
1efcd811-18f8-5b64-be29-9f46fbef3286	DUO_02_03
9e1ccd85-e76b-559b-9c13-ac2202a04849	GENKI3_L03
788e64aa-46ee-574d-aad9-83c8b56bc40a	GENKI3_L03
f7d20fe3-57b3-5e6d-85f6-5b54b0ba7088	GENKI3_L03
61637bb4-cc4a-52be-86fa-7fb678ab6e62	GENKI3_L03
f05e2adf-83c4-5ccf-a74a-fbd5664dceb1	GENKI3_L03
50cbf96a-57d3-5f45-b5fc-e415ff57717a	GENKI3_L03
50cbf96a-57d3-5f45-b5fc-e415ff57717a	DUO_02_03
243bdf44-b101-5d5d-bc51-a4f5c1c03bc3	GENKI3_L03
27c14160-d0d6-51f8-b7b1-9172cf8b84d2	GENKI3_L03
b8a9c502-6f50-5d50-96f1-417feef69161	GENKI3_L03
b8a9c502-6f50-5d50-96f1-417feef69161	DUO_02_03
87e72b5b-0332-5b61-b21c-ea8cc6a05c1f	GENKI3_L03
615aadf7-6900-5c48-814a-d6cc0884ae86	GENKI3_L03
bd0e8ffa-f7cd-5b27-8481-6ecb87512f6c	GENKI3_L03
bd0e8ffa-f7cd-5b27-8481-6ecb87512f6c	DUO_02_03
28166f6b-3aba-5230-9bd4-18e1a467bc3f	GENKI3_L03
28166f6b-3aba-5230-9bd4-18e1a467bc3f	DUO_02_03
45b4593b-f7a0-516e-84c4-68c7a7099243	GENKI3_L03
45b4593b-f7a0-516e-84c4-68c7a7099243	DUO_02_03
66e4cf05-72cd-512b-a462-05f4e69906b5	GENKI3_L03
10567940-e3bb-5257-b33c-ae9976bfaddd	GENKI3_L03
5800b221-4eb5-5331-8c6c-413e2d08c007	GENKI3_L03
9e9dc42e-df1e-5ace-948a-9dce72caaceb	GENKI3_L03
349836a3-2523-5f85-a7c1-f85e49c73f27	GENKI3_L03
fc5427a3-a54b-5c04-8b36-a570b8e7a72b	GENKI3_L03
311f8bec-e54c-5317-8880-dfd3e39d23a7	GENKI3_L03
1e68ea18-9d69-5761-a463-510eab20d1d3	GENKI3_L03
818210e0-641a-56f3-9241-7d24504480e2	GENKI3_L03
b8b492d0-5612-5ac1-bb4d-15f03e959c89	GENKI3_L03
02115b33-cd19-587a-a3f2-446dea254e33	GENKI3_L03
d7dc5e63-a1ca-58e6-9c31-047e23342953	GENKI3_L03
31df08c1-4538-5a5f-aa61-af9e88805bd5	GENKI3_L03
7d85fe8c-2226-573f-a219-0f2def303b50	GENKI3_L03
4fe504e0-3bf5-5480-98e6-5e626dddc417	GENKI3_L03
6e231f76-5724-5b3c-acfc-cdee6e2eb3a6	GENKI3_L03
\.


--
-- Data for Name: spelling; Type: TABLE DATA; Schema: vocabulary; Owner: postgres
--

COPY vocabulary.spelling (id, vocabulary_id, spelling_kind_code, value) FROM stdin;
2f2dd2b0-98eb-5fa3-82e9-27b35f638d92	f552ea3a-8b7f-5433-aa20-4c93a6868fb3	HIRAGANA	おはよう
2e39130c-b534-53dc-81e4-b65ac51ef332	8359d7d1-d6d6-5995-962e-3f6566232911	HIRAGANA	おはようございます
7bf03726-849b-5946-b0c1-e426d41b3551	4c55fd59-1160-52d2-8383-00f292668c1f	HIRAGANA	こんにちは
f5c3d443-f368-5c17-bc3e-565c4e3be6ca	8d46e404-38ff-518c-a25d-cb95d6f2c511	HIRAGANA	こんばんは
5db7f2a8-e3dd-56e0-8e19-3349d92b0aea	1184380a-27db-5f8e-841d-7b105c2a7366	HIRAGANA	さようなら
b00cb6d3-68c2-5b23-94e3-89d121cf9f7f	3ecfb07b-0a66-555c-97c4-5af7cb806507	HIRAGANA	おやすみ
940a59ed-edfe-5908-87c9-421c4a74910b	3ecfb07b-0a66-555c-97c4-5af7cb806507	KANJI	お休み
853d8a20-9dd3-580f-acd3-0cbf1994afc1	a419bb62-f061-5ac6-856b-6f85002642c6	HIRAGANA	おやすみなさい
b9e54157-032b-5a34-8bf2-4a695a174e95	a419bb62-f061-5ac6-856b-6f85002642c6	KANJI	お休みなさい
70b3ffdd-dec4-5d99-a8de-2553945a745b	c7a645f7-256a-536a-a5ee-5f85e8ce1137	HIRAGANA	ありがとう
8181b875-2569-5bee-89aa-59645c7068dc	b626308d-6418-5a2c-9151-7a8a368f27a3	HIRAGANA	ありがとうございます
9c08a5c4-c6f7-5ff6-9bd8-351cd2e0bea8	1913dbc6-4712-549a-9274-dc3511cecbfe	KANJI	行って来ます
8eac0d70-9234-521d-998f-ea30ef7bce05	1913dbc6-4712-549a-9274-dc3511cecbfe	HIRAGANA	いってきます
de8a0b88-aa07-505c-8c52-76e3d33d883b	e7161fcf-fbaf-5971-8adb-70745ae24103	HIRAGANA	いってらっしゃい
2dacefc6-1027-5cf5-9b72-a73fc09d2e01	c2a4dbcf-dba1-5e27-964f-5e2f7b358725	HIRAGANA	ただいま
0d45707c-2c15-526d-8eb5-dc5d5bb498c5	b06abb09-c9bd-5beb-a7b7-d184bedf72ca	KANJI	お帰り
dadba29c-5f6a-50b0-9ec7-1d957ada3ca0	b06abb09-c9bd-5beb-a7b7-d184bedf72ca	HIRAGANA	おかえり
388f9709-4b78-5e37-b4be-ca1f340f80df	47b08527-885b-50bb-b538-180dc308199a	KANJI	お帰りなさい
03b1739f-0b9f-53fe-bc35-bfcc6cc2e00c	47b08527-885b-50bb-b538-180dc308199a	HIRAGANA	おかえりなさい
83f63ec1-9a22-5b1c-ad6f-bdfd29122950	797b03e3-1495-52c0-baf4-ffb9cb324089	KANJI	頂きます
ecf2fcf6-dd4a-5c84-b057-7de027c3b163	797b03e3-1495-52c0-baf4-ffb9cb324089	HIRAGANA	いただきます
fd5f67e7-6779-5496-b121-7a85d321eb98	59bad243-8b31-54f3-9d7b-6de0961bc059	HIRAGANA	ごちそうさま
4e531b79-f0cf-509a-b5b1-3b94046d5bdd	05436324-1858-5a2f-a2a1-a41d00f862cc	HIRAGANA	ごちそうさまでした
952c45ee-cad0-54ed-a463-073236117849	136d6d6d-0487-5709-ac27-2c376bfebd43	HIRAGANA	はじめまして
89245ad3-f3e3-5533-a7ae-61a976b44360	ddd5fd1f-f041-576c-a479-d8d9a3221370	KANJI	よろしくお願いします
b4530b71-39a0-5a3d-9130-db16a0ee944c	ddd5fd1f-f041-576c-a479-d8d9a3221370	HIRAGANA	よろしくおねがいします
e4968580-e81e-5df6-8133-9714aeee0fee	d43cc787-37af-5dd1-92b5-fc756f18298a	KANJI	大学
7720f5d4-fb34-57a8-8dff-ce224814165f	d43cc787-37af-5dd1-92b5-fc756f18298a	HIRAGANA	だいがく
17e0efb1-d7a9-5d07-b80b-9e37848b6ea7	3c890a54-15f9-56e1-9bd5-3c91274b6246	KANJI	高校
4bacb2b0-df5c-52ba-9f0c-e6a9ee44ed8b	3c890a54-15f9-56e1-9bd5-3c91274b6246	HIRAGANA	こうこう
26c33f84-32d5-5009-9667-2e36f7c76f93	6de86986-05ed-58da-a055-9d49c89f7a06	KANJI	学生
505c6e26-f11f-5a47-9047-25b9ec0453db	6de86986-05ed-58da-a055-9d49c89f7a06	HIRAGANA	がくせい
9c4e6d38-f55b-5b31-be2e-9a160cd11a75	d1cb9e5b-6cc5-5339-b33d-4e948229156e	KANJI	大学生
e0620b0f-0e03-539c-a48d-f6ed5e9b211e	d1cb9e5b-6cc5-5339-b33d-4e948229156e	HIRAGANA	だいがくせい
92fb378c-061e-54ea-88a7-26ccd8cdfd3e	efe5fea4-0476-5328-bd18-2a469a8b6c3e	KANJI	留学生
cb3e03c2-0f63-59c0-bf4e-9b0154c72a26	efe5fea4-0476-5328-bd18-2a469a8b6c3e	HIRAGANA	りゅうがくせい
1f548470-05f6-57a0-88d2-f70838631980	c21b42a5-1ef1-51cf-9402-199d2b562a47	KANJI	先生
88e40a78-7bc2-51ed-90db-1aae50653bd0	c21b42a5-1ef1-51cf-9402-199d2b562a47	HIRAGANA	せんせい
9b33e54f-79d4-5482-8188-5bf97515192c	057a7cd1-f1a6-50e2-8449-3b6f04c67305	KANJI	〜年生
fc65939b-8ffe-563c-a8d1-b625ace6f3c0	057a7cd1-f1a6-50e2-8449-3b6f04c67305	HIRAGANA	〜ねんせい
313fc7dc-5e8b-5f8a-b0ef-c4a7c4b27ca7	e71aac30-c949-5b47-b118-d02a5d703701	KANJI	専攻
9f378893-c062-5f8b-872f-f1190abac310	e71aac30-c949-5b47-b118-d02a5d703701	HIRAGANA	せんこう
af1d66c1-3693-5244-bc3a-379e1a79da8e	4b1d8b7f-6477-5881-9439-1a7f30b26c9d	KANJI	私
604c6906-683f-594c-9b2a-b84e7f95d4a0	4b1d8b7f-6477-5881-9439-1a7f30b26c9d	HIRAGANA	わたし
5df6641a-0be3-5524-899e-6fa4d0ad962b	f65b93fb-1086-5405-8163-394db3e83014	KANJI	友達
467c7d80-dee6-531e-8017-e553c5024e38	f65b93fb-1086-5405-8163-394db3e83014	HIRAGANA	ともだち
d955293c-2989-58f4-9e0e-ac989f6c809a	c26e8356-f73a-5ddf-b045-432580c37468	HIRAGANA	〜さん
b476718e-1352-5d4d-ba7f-40533eef9335	0ef6972f-5e7d-5b51-be7f-8f39a5fd5e0e	KANJI	〜人
3d509818-d77c-553a-9b0f-dbe7c0a7baa4	0ef6972f-5e7d-5b51-be7f-8f39a5fd5e0e	HIRAGANA	〜じん
3a927707-ed22-58af-a172-c9fa19381777	146b7705-d9cd-511f-988c-5b2cc7661b75	HIRAGANA	もちろん
509b8b44-bd6e-5c04-a3aa-67a090c82798	146b7705-d9cd-511f-988c-5b2cc7661b75	KANJI	勿論
660ff6f5-d27d-53a2-8a04-dc3bcdc8a92d	8bedfb60-1599-58b0-9568-5e72b8cf6b7e	KANJI	会社
088067a4-7d07-588f-9c06-c67ac32882e3	8bedfb60-1599-58b0-9568-5e72b8cf6b7e	HIRAGANA	かいしゃ
334c5e68-696c-578c-916d-010364f768d7	25f93b07-dc04-553a-8794-329cc907dbe5	KANJI	学校
20ed3904-409b-5c09-b335-6309e82f3835	25f93b07-dc04-553a-8794-329cc907dbe5	HIRAGANA	がっこう
1778e2d9-1209-5d58-9a55-d85c3878cdc2	37eba762-70cf-5c5c-b689-621f26c1ee5f	KANJI	小学
60bcf3c1-74f9-574c-a85b-04a679cccede	37eba762-70cf-5c5c-b689-621f26c1ee5f	HIRAGANA	しょうがく
fd0c12d6-2acb-552a-8db5-ae67d30d39ec	99cdf9f9-df8c-5d7a-9ea1-a7c003c9f289	KANJI	中学
d0d158d3-a7e7-5a96-9c5a-e8f231144bb3	99cdf9f9-df8c-5d7a-9ea1-a7c003c9f289	HIRAGANA	ちゅうがく
41a1e283-3de2-5e69-a35e-f256c427e4ed	947e6f87-e4fe-5b27-9bd3-215e8b1fc71a	KANJI	外国人
c02490fb-2215-5d4a-9e20-19aa694b99d8	dd191fac-e29b-5262-b2a6-f9f2875a4a3f	KANJI	住む
5f4854d0-f405-5aef-b032-b78a489a6e30	dd191fac-e29b-5262-b2a6-f9f2875a4a3f	HIRAGANA	すむ
e1592633-21b6-52c9-a445-6fcae3f630f9	fc82f2ac-3b17-582f-9259-7200e1430824	KANJI	京都
3de7d669-5ddc-5841-8a5a-bcaed0a139db	fc82f2ac-3b17-582f-9259-7200e1430824	HIRAGANA	きょうと
58c2bc1c-4af0-552a-b348-3b7e50afcfee	15c5fee6-dda7-5d40-b7fd-382d97a28b9c	KANJI	大阪
3ef6a3f6-bbae-5ff5-8133-2792969d4f5a	15c5fee6-dda7-5d40-b7fd-382d97a28b9c	HIRAGANA	おおさか
ea87f8a8-b40e-594a-bc78-dc29296cb1f4	39458010-a5eb-5b13-b054-4cc458702c56	KANJI	東京
e47dc2e2-4bdb-5632-9302-ceffdec49177	39458010-a5eb-5b13-b054-4cc458702c56	HIRAGANA	とおきょう
5bed6ed8-0d3f-58ae-86af-81af9ad50ff5	091d6d9c-5d19-51f5-bc28-3b5281042464	KANJI	申す
632bf264-1e66-5000-8173-750a6d991040	091d6d9c-5d19-51f5-bc28-3b5281042464	HIRAGANA	もうす
7c6a7f92-02ab-5e94-ad8b-f949dbd917c1	6e44df4a-4366-5c1a-86af-87a4e5f61fc6	KANJI	違います
19f013e0-d32d-5ece-a530-e4ec8cb4f4fd	6e44df4a-4366-5c1a-86af-87a4e5f61fc6	HIRAGANA	ちがいます
3a426749-0a9d-59a0-b31e-163325029c38	7748dd60-2126-5e30-a5b6-39596a0e25e4	KANJI	違いは何ですか
a51b7ff7-dacf-5be7-b44c-e5da68ed7b5e	7748dd60-2126-5e30-a5b6-39596a0e25e4	HIRAGANA	ちがいはなんですか
2db138e0-296e-57af-b161-a0841bd3b1ae	dce3fe6a-497c-54b2-a121-df6767084258	KANJI	今
78f51be9-33e6-59e1-b1f2-1a34ceadf88c	dce3fe6a-497c-54b2-a121-df6767084258	HIRAGANA	いま
c3171fe5-314a-5e45-8731-9abe72811c9b	42ffdbae-c27d-53a4-b113-95583953e16c	KANJI	午前
46cdc645-d4e5-590c-b1c9-0fdd8df485d4	42ffdbae-c27d-53a4-b113-95583953e16c	HIRAGANA	ごぜん
c13b47d3-6e89-5265-b429-7e805315a56a	94611381-0488-58aa-8cf2-12ad9ff4e2dd	KANJI	午後
6b73bd8a-1121-53da-a698-bdf1c1a54bfc	94611381-0488-58aa-8cf2-12ad9ff4e2dd	HIRAGANA	ごご
64633680-dfdf-5c40-bf59-c9a18d1c5591	40502e18-b4ba-50b4-89b7-23fd3b4d4a21	KANJI	〜時
b6307fa4-e750-5072-9cc1-456072b23a86	40502e18-b4ba-50b4-89b7-23fd3b4d4a21	HIRAGANA	〜じ
e95ef123-eaf8-569d-825b-be051e27e267	5372b023-b803-551c-9a7f-f404804f63f1	KANJI	半
87ce5bea-03fa-5a04-91d1-e7a95db235a4	5372b023-b803-551c-9a7f-f404804f63f1	HIRAGANA	はん
dd94312b-40ad-5d97-bffb-e4feb0b76f5e	2cf1d32f-b261-5ba6-829c-a9f1237a88d6	KANJI	~分
ca4b692e-0867-5fd3-bfb2-ce881f2a697e	2cf1d32f-b261-5ba6-829c-a9f1237a88d6	HIRAGANA	〜ぷん
227a4c31-bb55-54a7-bb05-80093a7cb329	a39e794c-9682-5cd5-9c5e-dd961aa92bc5	HIRAGANA	〜ごろ
67e21d4b-5197-511d-9c3b-598da51ddcba	a39e794c-9682-5cd5-9c5e-dd961aa92bc5	KANJI	頃
81f214fe-f9c4-5745-9c04-1e0b87dd62fa	9e67a677-9058-536e-85d6-389ea5138c86	HIRAGANA	ちょうど
976d29f9-bf23-5edd-ae40-d759c27b57db	9e67a677-9058-536e-85d6-389ea5138c86	KANJI	丁度
7aa45f0a-1602-55e5-8ede-e5a4821ffe14	252852aa-aefd-504b-b671-668a33020492	KANJI	零
cef316f8-c889-5b71-9c54-d543f35d6746	252852aa-aefd-504b-b671-668a33020492	HIRAGANA	れい
da8cdd76-87ee-5d35-9ca2-5340654a1069	cc6a4913-6f39-521b-a0cf-9631f640800e	KANJI	日本
5c7b43e0-ee24-5a43-a234-de4e64db32bd	cc6a4913-6f39-521b-a0cf-9631f640800e	HIRAGANA	にほん
f8e54dc8-4fde-569a-9bd2-cc10924c1197	522e0796-297d-5bf2-be85-e93596baa6bf	KATAKANA	アメリカ
8ef18341-fc94-597f-8c35-7a525fec4d50	522e0796-297d-5bf2-be85-e93596baa6bf	HIRAGANA	あめりか
a7e5fa4c-e482-5b11-a2e7-478283493467	a420a37b-593e-572e-aae7-d724eca6803d	KANJI	〜語
80addbcf-6579-5145-b127-7e712b37587e	a420a37b-593e-572e-aae7-d724eca6803d	HIRAGANA	〜ご
8ec71c62-6b1a-5b96-bc80-f8299a8ea96d	c59169cb-e5ad-5311-a9ce-43764a5ddda6	KANJI	〜歳
f263cfef-ae70-5d80-8789-cb00ee27219f	c59169cb-e5ad-5311-a9ce-43764a5ddda6	HIRAGANA	〜さい
ab67f8fa-6dc7-5388-8835-dcc9b3eb8cf0	1731f988-dcfb-5b31-bda9-c8988a5711bf	KANJI	電話
3804b3b5-a865-55f8-ab40-d1dd5e7819da	1731f988-dcfb-5b31-bda9-c8988a5711bf	HIRAGANA	でんわ
522dc3a3-3c47-50d8-bf67-33f6598d9d15	f6524cfb-fa66-56ae-ae4c-0e38a8b6e54c	KANJI	〜番
b08756e4-b14f-57df-999f-2425e70e603e	f6524cfb-fa66-56ae-ae4c-0e38a8b6e54c	HIRAGANA	〜ばん
eb6016b9-819e-572e-a45b-55673bcb2ef4	d8f98686-282e-5c5f-88b2-4e046e707e54	KANJI	番号
e7af3ad4-b7eb-5aaf-b2ac-946c4609211d	d8f98686-282e-5c5f-88b2-4e046e707e54	HIRAGANA	ばんごう
f6b82f79-75ee-5c63-a532-f258ce7d6f0e	728f5a87-3848-5450-a941-f9cd0f898067	KANJI	名前
d71ea796-b5fd-55e4-9cff-38834b897d0b	728f5a87-3848-5450-a941-f9cd0f898067	HIRAGANA	なまえ
94211400-d68e-5f0a-b5ba-724ea1b1f285	507092ff-6dc3-5749-a9c3-79ba8d2bc360	KANJI	何
0da5c7c8-ad23-5957-859a-42f77031451f	507092ff-6dc3-5749-a9c3-79ba8d2bc360	HIRAGANA	なに
cb73f875-d21e-585c-bb5f-3583315a2ac7	507092ff-6dc3-5749-a9c3-79ba8d2bc360	HIRAGANA	なん
42c5cc87-b98b-5f1e-86c7-1efcf1836bec	84e857b9-5605-51fb-bfb3-8cc08eb579d3	HIRAGANA	あのう
2b7913d9-04af-52d8-a8b5-e49b2a7f3628	dde2be42-c934-53fc-9dc6-3ce12b727924	HIRAGANA	はい
200e8dda-038f-5fe5-b1f9-d83a539f9e4b	0428342a-6b57-5e6f-b1c6-4aa6ccfdb267	HIRAGANA	いいえ
88bc0db8-206b-504f-8db2-f1435e733a21	a48cfff2-551b-5525-bf29-f706571e7e15	HIRAGANA	そうです
fb06d772-7e41-52e7-a23e-dfdd80ab45f0	311cf3c0-5798-551b-a933-eb33c4ce7843	HIRAGANA	そうですか
08c18f75-25e0-577f-a2a9-1d95d4ff1a56	40483fe8-7fda-56b7-b28b-eedb9da50a52	KATAKANA	イギリス
df0854bc-e9f0-5ab6-9a57-c2cc9f7ffecb	40483fe8-7fda-56b7-b28b-eedb9da50a52	HIRAGANA	いぎりす
95f1289c-15a1-54c6-9220-2840d7e9d9bc	2a0ff39c-471b-56d1-a2b2-39f20596f70c	KATAKANA	オーストラリア
4a20975a-64ea-5aa7-9168-86554d3891e9	2a0ff39c-471b-56d1-a2b2-39f20596f70c	HIRAGANA	おおすとらりあ
eaf53b3e-7a17-53cd-877c-b4ea789d2ca5	5cb08712-5c04-56f0-aaf6-a8e76a1e0ef4	KANJI	韓国
158c5216-a31d-5cc5-b554-5d6d6cc033a9	5cb08712-5c04-56f0-aaf6-a8e76a1e0ef4	HIRAGANA	かんこく
083dbcf2-4cc9-57b5-80d2-a5b50d62f1a0	b4a146e5-f483-50d5-8b18-ca71fdb3f570	KATAKANA	カナダ
05f45690-8e1d-5f99-8d69-59e7ec26a9f4	b4a146e5-f483-50d5-8b18-ca71fdb3f570	HIRAGANA	かなだ
dbbac175-c623-577a-a7ef-4c9c815950f3	54ca6cf2-c9f0-5a77-b53c-8e4a2fbe76ac	KANJI	中国
62bf6317-c9f5-53d8-84e9-c208cc4689f1	54ca6cf2-c9f0-5a77-b53c-8e4a2fbe76ac	HIRAGANA	ちゅうごく
00f00677-c3f9-53d4-abe1-b437b2a34eb6	f3f3ba33-de3b-5fa1-9b99-eee62ae2e024	KATAKANA	インド
6be06087-8a55-5aa4-9a5f-c3bf69eca487	f3f3ba33-de3b-5fa1-9b99-eee62ae2e024	HIRAGANA	いんど
d1877ff8-19e0-5a7c-8c8e-eb840d38d144	eb20360e-5c1a-58b8-9bf8-040795c5bf0a	KATAKANA	エジプト
931fb016-2c54-59d7-9772-285ca5b85264	eb20360e-5c1a-58b8-9bf8-040795c5bf0a	HIRAGANA	いじぷと
9bb2367f-e815-5505-baeb-efdd53ccf5cb	ae144a7c-293f-5f24-81ee-37d5594d6aed	KATAKANA	フィリビン
e2d3accb-0079-5258-9ea1-235f0a5dc6d6	ae144a7c-293f-5f24-81ee-37d5594d6aed	HIRAGANA	ふぃりぴん
227c9697-18eb-5d28-97b4-dac3315cbba1	abd1e50d-f284-5a21-831f-344cb338414e	KANJI	アジア研究
2949e58e-ec62-51a1-b054-c173a31db85f	abd1e50d-f284-5a21-831f-344cb338414e	HIRAGANA	あじあけんきゅう
42d60a40-2739-526d-9aa2-f143b84cde77	13912d04-d947-5c72-a192-740c19a28c86	KANJI	経済
88b80e93-dda8-5699-839d-3c204c735d20	13912d04-d947-5c72-a192-740c19a28c86	HIRAGANA	けいざい
612e9cfd-044b-5525-a286-ce4b2cb9639c	7672ef18-d91f-548f-a938-970d6a741586	KANJI	工学
c85d20ac-6223-5d18-95a4-3b2e5696d50d	7672ef18-d91f-548f-a938-970d6a741586	HIRAGANA	こうがく
f30e248e-f63c-5f76-b101-affd50e0d861	471eeab4-7852-53f9-ba6c-342f13746958	KANJI	国際関係
a565cd3e-3048-50de-b733-d6bc57653492	471eeab4-7852-53f9-ba6c-342f13746958	HIRAGANA	こくさいかんけい
39ca3b74-f47b-55bf-b3d8-611f8b253473	9e62feab-62dc-5faf-92d9-8cd423af7dab	KATAKANA	コンピューター
25a0153b-54e0-56a9-9f79-5b9e66aa8281	9e62feab-62dc-5faf-92d9-8cd423af7dab	HIRAGANA	こんぴゅうたあ
04d388eb-ebe2-59f5-a595-0a49fd2ba6b1	15b8fb86-6025-524c-a968-0d8e442c0d8e	KANJI	政治
14d61063-55f9-597c-8d90-a7cae6788659	15b8fb86-6025-524c-a968-0d8e442c0d8e	HIRAGANA	せいじ
d8b591b9-6712-5bac-877d-a0ea7a35ab02	ee82d23f-991e-59ba-9646-d407d35b46ae	KANJI	生物学
b37e587e-31a3-51fe-99d1-83b0269a2a04	ee82d23f-991e-59ba-9646-d407d35b46ae	HIRAGANA	せいぶつがく
799f7b6a-056b-5c19-913f-25ed551f5a80	a74f0164-da3d-59dc-bbd0-39cf065ee813	KATAKANA	ビジネス
882e2ee9-48be-55dd-80e7-9f10bc83a978	a74f0164-da3d-59dc-bbd0-39cf065ee813	HIRAGANA	びじねす
06962e25-3684-5a8b-8ec8-4df09922b020	f41b3958-7c0c-5c05-9ea8-d0099b014f0c	KANJI	文学
505c4b7f-5afe-5f48-bc87-02970eed49f6	f41b3958-7c0c-5c05-9ea8-d0099b014f0c	HIRAGANA	ぶんがく
9737618f-ec2f-5332-b82d-6d159da9bf45	0d91d253-e394-572c-a6c4-37e20ed0f603	KANJI	歴史
fa6838d5-7433-559c-8a54-d209a7adcbc8	0d91d253-e394-572c-a6c4-37e20ed0f603	HIRAGANA	れきし
704f5431-5607-519a-9e09-14bb15c23829	a9d318d5-97e8-5990-916c-fd300d951e2c	KANJI	物理
9f194084-f37c-5782-8d8e-3479286f065a	a9d318d5-97e8-5990-916c-fd300d951e2c	HIRAGANA	ぶつり
03ddbe7d-3d2e-5974-ad4a-2a87227e2822	45983eaf-745d-5a47-bd34-aa09444736a4	KANJI	医者
7446be24-2622-5886-ab93-bd61ced50f18	45983eaf-745d-5a47-bd34-aa09444736a4	HIRAGANA	いしゃ
e261a558-0012-5d0c-88a5-48ebbb737894	b01b2de0-f80a-587a-99bb-8e7e2157feec	KANJI	会社員
6b8bb68a-01e9-59cb-8da5-040b6c62fe63	b01b2de0-f80a-587a-99bb-8e7e2157feec	HIRAGANA	かいしゃいん
2a2597da-a15a-53b3-a405-5695abfb8878	a2bb0866-9ce3-5b9a-bc60-49b48a40e40f	KANJI	看護師
ddd96c7a-994f-5a38-8df3-2a02a02dcef3	a2bb0866-9ce3-5b9a-bc60-49b48a40e40f	HIRAGANA	かんごし
98a23c10-bc4c-59bb-a8a0-a2f045f26cd6	c511ec54-4f00-5426-89ef-ee1558ccfb60	KANJI	高校生
e50eb0fa-f335-530a-a6b2-62eda61703e7	c511ec54-4f00-5426-89ef-ee1558ccfb60	HIRAGANA	こうこうせい
9901e691-b5b7-5d8e-9fd7-6b5e3979838e	7d3f6d4b-fd98-5a19-ba56-b7c707092305	KANJI	主婦
4046a954-51f1-547c-bb1d-468bedf58c2e	7d3f6d4b-fd98-5a19-ba56-b7c707092305	HIRAGANA	しゅふ
c628260d-5be1-5d42-90b9-2768cd7babfc	98e48ea2-8566-55a1-bfb8-d5aaf1a060d0	KANJI	大学院生
ac373c9e-df68-56f3-9fa3-1e6b55f8e269	98e48ea2-8566-55a1-bfb8-d5aaf1a060d0	HIRAGANA	だいがくいんせい
61e38cd1-2056-5ac6-8677-65588bdd2931	902b7ccb-091c-502b-a413-5a3a38c655a4	KANJI	弁護士
3ba95a23-60ff-55db-9ba1-efd20c78aff7	902b7ccb-091c-502b-a413-5a3a38c655a4	HIRAGANA	べんごし
d53d0bf9-593c-58a9-b66e-81fcb6d099b6	f8d03abe-255a-5717-811a-91f511c39759	KANJI	お母さん
fcfaa861-5338-5679-99f5-48dcbe985ffe	f8d03abe-255a-5717-811a-91f511c39759	HIRAGANA	おかあさん
f89b954c-4d91-510c-b4bb-181272ebf76e	d7a063c8-998c-50a3-825c-86f896a5a0c7	KANJI	お父さん
bf7368c4-7523-5f4a-a5b8-feefb78ce120	e09e6607-0754-53e7-ac90-7d5eec6a3442	KANJI	お姉さん
08a0c72f-4022-5482-b122-984c5e11feb3	e09e6607-0754-53e7-ac90-7d5eec6a3442	HIRAGANA	おねえさん
a9c6299d-42b6-5930-a6b7-017168a82325	942ce296-4fdd-5a7d-9c2c-53d4c2ae2de7	KANJI	お兄さん
e4ff269d-20d1-572c-b2f3-46570862024e	942ce296-4fdd-5a7d-9c2c-53d4c2ae2de7	HIRAGANA	おにいさん
26b801ba-c20b-5d4c-ab1f-a198b60095fb	d4387364-3f1c-51be-9762-ddf41f844fb7	KANJI	妹
4c36db95-5c99-5c28-95ce-7c5efc534704	d4387364-3f1c-51be-9762-ddf41f844fb7	HIRAGANA	いもうと
159b5cf4-b222-5de7-bec6-ddea8f6e129a	acc5e753-294b-571f-9ee7-a7cd6eeea235	KANJI	弟
6e34b4d0-688d-5a5f-ac40-5e3b66767f11	acc5e753-294b-571f-9ee7-a7cd6eeea235	HIRAGANA	おとうと
a6108ee1-eb7a-5638-aa7a-4185eb24ea38	58ecba76-692b-5baf-a096-62bf3e752da2	HIRAGANA	これ
882616f1-e948-57d0-83c8-5547632e09d0	8373ee97-fb02-5fa4-9f3a-e6d0bfe42042	HIRAGANA	それ
ef19ddc0-c4d7-55f4-89f1-60add525833c	e3286acb-a31b-5fb6-9c16-28ac16fcc269	HIRAGANA	あれ
d27869d8-0951-5cac-8b3c-b6dd33774d56	9ef8e062-db86-5059-82be-ccc85580d5e7	HIRAGANA	どれ
859e7825-dad8-599e-b1e3-7200317f683b	430b4ea9-6092-52c7-b2bf-4300de6196c7	HIRAGANA	この〜
c15b0e83-fa7c-51e5-918e-fe84c939c64e	9d9f54be-2a19-5147-8935-e7c3c887fa03	HIRAGANA	その〜
e58ad2ce-ea60-5c13-bf9a-990479cbc401	908efe1a-b6e9-55d5-b550-461cea175af4	HIRAGANA	あの〜
967de66d-2c31-5a9e-a0c3-53f055b947a2	a9b7cffc-2d14-58ae-818b-3ac3b6e6e4d0	HIRAGANA	どの〜
c5c9d413-7ee6-5382-9fd9-90bfacc2a0ea	9c591bfa-c4a7-5263-b2b1-7c8d14e10c34	HIRAGANA	ここ
3b61d4ce-617d-5d28-b2a7-e5b3ba22aec5	0279527b-c398-5856-aad3-11b834907f56	HIRAGANA	そこ
65ea426d-2bbf-5cc5-87dc-5aea98c944be	5587745e-e524-5159-8fe6-1e8637b90455	HIRAGANA	あそこ
cc558a9f-b656-50f4-a28d-9f423dabf927	1502a2cb-059c-5a9f-baab-af68a0d09ac8	HIRAGANA	どこ
3b4881e5-3e73-5ad1-8243-899a56ea4ea5	93eaf294-a292-5980-b17e-de43755335c9	KANJI	誰
c8349837-0066-5f4e-9fc1-af46934647ed	93eaf294-a292-5980-b17e-de43755335c9	HIRAGANA	だれ
974bf955-005b-53b4-9789-55beebb954e5	f5fce96d-2982-5d7a-b7ce-9dc8914c6ab3	HIRAGANA	おいし
704b6cd4-fb3b-5d9e-b4cf-e63dd0c99ebb	f5fce96d-2982-5d7a-b7ce-9dc8914c6ab3	KANJI	美味しい
233a127c-3faa-5b59-b566-960b167a2899	10a5183d-9b5d-58c7-885f-0210e56c1dca	HIRAGANA	うまい
c0560764-63de-5057-80e9-e00544932f93	10a5183d-9b5d-58c7-885f-0210e56c1dca	KANJI	上手い
34e6a894-7b53-5396-8845-8ca64029b2ff	f7aa023d-a413-562b-bf45-75367462a4b2	KANJI	魚
4a87d97c-dc60-5885-9422-64a4b4dca58a	81b8f353-cd1c-53e4-a0f4-1630c490c959	HIRAGANA	とんかつ
a80baa57-ca13-51b3-a2ef-b4b5cc7a8858	81b8f353-cd1c-53e4-a0f4-1630c490c959	KANJI	豚カツ
a5c6cf98-c919-5800-8116-fd22541b26e8	e2c0e2f3-891a-55e3-ad28-285669d27287	KANJI	豚骨
4dbe0df7-565d-5d4e-a9c8-a072e496707b	e2c0e2f3-891a-55e3-ad28-285669d27287	HIRAGANA	とんこつ
ee5f5c8b-28e4-5b29-adc9-54743cee511b	2dd4acb2-11ca-50c5-b365-c22616ea316d	KANJI	肉
50cf4cc0-608f-51a6-8d56-c46ff556040d	3ab19c55-4c6a-53e0-afce-41968363d92a	KATAKANA	メニュー
aaf68fce-fdf4-54f7-9cba-dd73ea2d112a	3ab19c55-4c6a-53e0-afce-41968363d92a	HIRAGANA	めにゅう
0db87bcf-5537-579b-8632-e5e07110ff5a	496222cc-1cb3-50c8-9e7e-bbc7d327b78c	KANJI	野菜
b5c1b15c-a3d6-5dbf-ba4e-39ce8ed1007e	496222cc-1cb3-50c8-9e7e-bbc7d327b78c	HIRAGANA	やさい
76b72784-202e-5c9d-bc12-d0e39e7ed1ff	4fbc0a01-e32a-5449-8efe-3ae37aee4f2f	KANJI	ご飯
59a4fea6-3b71-5e21-ae10-1af3386fb32e	4fbc0a01-e32a-5449-8efe-3ae37aee4f2f	HIRAGANA	ごはん
3b766a57-2e25-51fe-a01b-df14fd28384a	b74ca7fa-4622-5a52-b9df-5260878e687b	KATAKANA	パン
2ff20629-f937-5c9b-a542-7c63dd107997	b74ca7fa-4622-5a52-b9df-5260878e687b	HIRAGANA	ぱん
cf3f76da-4fec-54f8-b6ce-5aa132a99f25	5e2b2d0d-7461-56e2-a151-9a10375d5615	KANJI	卵
3ee76ed2-f30e-5f35-930e-8545f1212b53	5e2b2d0d-7461-56e2-a151-9a10375d5615	HIRAGANA	たまご
8a216bf1-3249-55a0-baf7-1d0244a750a5	12c11165-7996-5b13-bf19-c3afc419afb2	HIRAGANA	りんご
97c79d15-8610-5884-864f-c3050730ffb0	12c11165-7996-5b13-bf19-c3afc419afb2	KANJI	林檎
593ec946-041d-5815-8e32-02c86f1ab10c	b1852be8-7a0e-5541-b94d-29be625cb423	KANJI	傘
195aaa68-b2db-59a4-bfb5-f865a7698857	b1852be8-7a0e-5541-b94d-29be625cb423	HIRAGANA	かさ
6af3c638-ed95-58fa-a1cd-8a21db67641f	b5d661b7-f275-5647-a86b-cf4bd963f512	HIRAGANA	かばん
5c3e9023-77bc-5efd-9027-c1c964f345aa	b5d661b7-f275-5647-a86b-cf4bd963f512	KANJI	鞄
34784e82-6341-5dc5-b4a6-db5ddbe97faa	96fb26f0-20df-51e0-b1c4-793ab736c2d9	KANJI	靴
9a413add-7cb1-56da-9243-5b7a647098aa	96fb26f0-20df-51e0-b1c4-793ab736c2d9	HIRAGANA	くつ
b1d59157-8068-5154-ac3f-c35178461371	b802b5a4-5804-5f5a-a193-e475bceaf5dd	KANJI	財布
afa50ef6-3c71-54ba-8201-ec016fe4c7ab	b802b5a4-5804-5f5a-a193-e475bceaf5dd	HIRAGANA	さいふ
7654498b-681f-5f20-a8f6-d363ba399dea	bf5e0762-effe-5d38-8786-503cde98e504	KATAKANA	ジーンズ
7d8b3a4d-61fa-59a2-ab9a-fa87fe0bc821	04bddd51-0749-5efe-9946-6d326f61bcb6	KANJI	自転車
25bfe68a-7602-5388-98b1-5701dd8717f8	04bddd51-0749-5efe-9946-6d326f61bcb6	HIRAGANA	じてんしゃ
37b05dda-b3db-5c66-a394-6c89059dc6da	f8a3e40d-f42e-5ba2-995d-3abc33e63a41	KANJI	新聞
fd2a4110-3c48-5a39-81e1-074eec67a75d	f8a3e40d-f42e-5ba2-995d-3abc33e63a41	HIRAGANA	しんぶん
1a323805-e9bb-57c6-851b-45525dba8f69	e7323b54-9fa5-5cb7-8d68-d2ebe9b379ea	KATAKANA	スマホ
a8548ac9-a03a-50d0-88c2-2c96c8048dd2	1a0585e5-3bd0-5903-90cc-d3381778da68	KATAKANA	Ｔシャツ
6abd1ce0-a561-5b69-8720-e941239e0050	1a0585e5-3bd0-5903-90cc-d3381778da68	HIRAGANA	ていいしゃつ
ab3b6710-c8fe-52ab-b814-1cfe807989e3	1c5e45ac-cc4a-5d53-b566-d6d832828ba0	KANJI	時計
70964b04-78fa-5557-966f-cb4ad75b7db1	612eac67-3ec3-535e-94a3-249d60f51046	KATAKANA	ノート
3314b6b4-06a9-567c-8f20-9cc070af51ab	442d1c8e-a848-5fa4-8fc9-d398441b4b65	KATAKANA	ペン
5e0736d2-2730-53b9-98e4-5dd5a7533ece	5f6cc5c3-6625-5caa-9277-f43493360fc2	KANJI	帽子
0c2a2e9e-9d89-5418-bbf1-7374dca12535	5f6cc5c3-6625-5caa-9277-f43493360fc2	HIRAGANA	ぼうし
112520c5-b908-554b-8d1a-ad6a989105bb	6d055f49-622a-5d8a-a7d2-11de3279b9ce	KANJI	本
ad4d30b3-7fd0-5e08-974e-8aeaa4c73d35	6d055f49-622a-5d8a-a7d2-11de3279b9ce	HIRAGANA	ほん
80ef54f2-c504-5795-92bc-106b0837855f	3a52dad1-de68-523b-b6ad-3148496bcf8e	KANJI	銀行
58cc22c2-b05b-591b-9bf2-806daa240e3d	3a52dad1-de68-523b-b6ad-3148496bcf8e	HIRAGANA	ぎんこう
23c3b38a-7ddb-5fa6-bca0-246662b6b6a8	6c108fe0-b52d-56de-96f2-f8e0bf450367	KATAKANA	コンビニ
fc74efc7-2d6f-56da-9b69-67895103219e	14edc8de-8ed3-58f5-a083-13a34db6f490	KATAKANA	トイレ
0372f56e-6b3b-55f5-91f6-bea000e24fca	dd52de46-6d78-5bc3-96ab-b25e9be9e291	KANJI	図書館
663d3405-daa4-5b6e-9707-3b9d7f90c2a9	dd52de46-6d78-5bc3-96ab-b25e9be9e291	HIRAGANA	としょかん
396a3e6c-045a-5e7f-ab9c-d0211e616070	89b553be-905d-57aa-946c-86dc9f366aa2	KANJI	郵便局
7b1755bf-7f10-5127-b55c-7fbe538445d0	89b553be-905d-57aa-946c-86dc9f366aa2	HIRAGANA	ゆうびんきょく
13f0c34e-6b90-5fdf-b8fd-ac54f5e8c785	f7d4fade-03e1-53e3-94c0-24034e6a9e08	HIRAGANA	いくら
6e827086-043d-5ef1-914d-28f1ff4c4740	f7d4fade-03e1-53e3-94c0-24034e6a9e08	KANJI	幾ら
4b5063b7-4536-581f-ab42-a6aa0671bede	fbae6594-96c7-53f0-a80b-bd325d5a22ff	KANJI	〜円
403665f7-94e6-5cfa-b267-55a517aff2b9	fbae6594-96c7-53f0-a80b-bd325d5a22ff	HIRAGANA	〜えん
0ab19f69-3584-5bee-b551-97f796a4ffaa	dcb08e4f-eb7c-5536-bd57-ee51127a14be	KANJI	高い
7d97e7ed-05f9-577f-a87a-0e1dacc834ae	dcb08e4f-eb7c-5536-bd57-ee51127a14be	HIRAGANA	たかい
9d6976b3-1495-5b61-aa0a-12eaf307c5e0	1cee44df-fc33-5279-8d09-5051e714782f	HIRAGANA	いらっしゃいませ
326f6a64-a341-586a-ac47-08a8353b1b18	845d24c6-6c7c-50f4-8dd7-450485839fa5	KANJI	〜お願いします
66739f6a-e449-5698-9fe0-114cd8d9f8ab	845d24c6-6c7c-50f4-8dd7-450485839fa5	HIRAGANA	〜おねがいします
6db532f0-8d3c-5767-8e4b-9987eff9d49a	fc6be215-8cf3-55b8-8a6b-0dd345852963	HIRAGANA	〜ください
3f8143f1-d336-5115-b5d4-d1439274829a	3bb29baa-6e40-5c58-93cd-2000ea1120eb	HIRAGANA	じゃあ
4733d6d5-c7b2-5c75-a20b-b7182d3ca297	8dc54b41-91cb-5289-bb0b-5f4a5854fea7	HIRAGANA	とおぞ
7f66db47-afc1-5499-b196-5478b7736279	5891a34d-ba0a-5458-b42e-f82c60d27f9f	HIRAGANA	どうも
29140c19-30c1-53aa-b840-9a8dd9b4f3a9	48b8587c-d363-5b54-94d9-c9e5dfff2412	KATAKANA	ゼロ
f8c15ef0-c171-59ef-9e7b-7cbceb015969	c007b505-323f-5517-8353-ed093d1571de	KANJI	一
714bcad8-b14f-5f85-b9d8-64fd3ab5c786	c007b505-323f-5517-8353-ed093d1571de	HIRAGANA	いち
a12148a7-c033-5afa-85f5-a3cb5f665512	8873834d-7358-505d-a93b-7782dbd00024	KANJI	二
4b9c0c09-3acd-56a1-8c54-271f16ad0212	8873834d-7358-505d-a93b-7782dbd00024	HIRAGANA	に
12ad3482-9179-5312-bb91-e49fc9499612	0f9e7c25-4cfa-5186-a893-0f59b7e34745	KANJI	三
afe3e46a-e7e1-5c87-8a31-e5d0355dd42e	0f9e7c25-4cfa-5186-a893-0f59b7e34745	HIRAGANA	さん
244332d4-fe91-5051-958f-ad2b62370f81	59d58d35-f0f2-5535-8404-8cb786e2c830	KANJI	四
ee62d46d-0f52-53d3-b866-efba4650aa45	59d58d35-f0f2-5535-8404-8cb786e2c830	HIRAGANA	よん
ee4bbc0e-84f8-5e9d-b10b-53c05de51a1c	59d58d35-f0f2-5535-8404-8cb786e2c830	HIRAGANA	し
cb3e072b-a2c0-56a6-b10f-498f6d1c5b81	82339058-2123-58a8-93dc-b1c0b05cdae8	HIRAGANA	ご
2491792c-5ac1-5fc8-95e7-86ea5fda6259	e469a7d8-cfad-5a85-ba5c-cb1d0c8ca1b0	KANJI	六
32066cdd-df5e-5ef7-a364-9bb86df93b69	49ab0509-4a86-552d-975b-fc49e74790d4	KANJI	七
c24ad673-299d-55f9-8f78-7717388ff80e	49ab0509-4a86-552d-975b-fc49e74790d4	HIRAGANA	なな
731f72c6-1431-55c3-b1c2-563276f2b387	49ab0509-4a86-552d-975b-fc49e74790d4	HIRAGANA	しち
40c4ab0c-9f4a-5200-9bd2-dca06422c461	6d9b3812-644e-5890-94cc-09a2fd01a30a	KANJI	八
331456ae-1d14-5a3d-baf1-7a667bb20821	6d9b3812-644e-5890-94cc-09a2fd01a30a	HIRAGANA	はち
35175425-22ec-5ab5-9180-3eb5e9442734	aaf16bcd-1ecc-5f72-97a6-497825719aba	KANJI	九
c5a1fded-3782-57c2-989a-f0bfb84ea40c	aaf16bcd-1ecc-5f72-97a6-497825719aba	HIRAGANA	きゅう
31ff8e41-0414-5d3b-b96f-66b39686b2d7	e05ed6a4-edcb-5052-9760-1e10c70c2236	KANJI	十
dea1d1b7-1ea2-5afa-90e0-7676713a7f9d	e05ed6a4-edcb-5052-9760-1e10c70c2236	HIRAGANA	じゅう
8b1b7f5d-b982-55e7-9de7-4a9da5c8a8d7	0f06fb63-c508-57a2-8983-2cb9f5dfd2e7	KANJI	百
d018bbde-937e-5e7b-aed7-98080dc593f9	0f06fb63-c508-57a2-8983-2cb9f5dfd2e7	HIRAGANA	ひゃく
fd2b4776-fa1e-515d-b945-91e977f7299e	662aaf34-2fd3-5ab7-ad40-ee6e4862fc2c	KANJI	千
ffbebe7c-10ed-5531-93d4-80265c2ae072	662aaf34-2fd3-5ab7-ad40-ee6e4862fc2c	HIRAGANA	せん
a44ee792-e394-5804-8707-386592aeb81b	06eedaf7-2c40-54e4-a1ea-669b265fda8d	KANJI	万
fceb9b86-3f97-5f04-b522-b533ea61cbbd	06eedaf7-2c40-54e4-a1ea-669b265fda8d	HIRAGANA	まん
ae3a4b03-6b24-5e39-afe0-6a94f152cdd8	b82fa253-fbb7-559c-b127-77d2262df800	KANJI	映画
4c808a04-86a9-5e18-95fc-6458a29d5e3b	b82fa253-fbb7-559c-b127-77d2262df800	HIRAGANA	えいが
9e315722-56b7-5a67-9be1-213fa5c90370	aa05a718-c2a5-5083-9c3d-b0e4d1bd09a8	KANJI	音楽
be93cd6e-647a-56a3-8a7d-ca9edf0c9bc5	aa05a718-c2a5-5083-9c3d-b0e4d1bd09a8	HIRAGANA	おんがく
7bda97f5-7b2c-5356-b54e-792d06ecca63	3bef2c81-886b-5bbd-9f1b-dfc94288bea2	KANJI	雑誌
a262a3f6-c2e5-542e-81cf-b2801d2e3a19	3bef2c81-886b-5bbd-9f1b-dfc94288bea2	HIRAGANA	ざっし
03f21ed6-b87f-5881-999d-a9c2a44cd852	bbf5a302-a23c-5142-b721-2aa90ff1e8dc	KATAKANA	スポーツ
cd5d9ef0-6238-5b4a-82f5-45bfa4cb6268	738002f3-36da-5668-8a8b-46415de8cd52	KATAKANA	デート
a64a5d7a-2ee9-51d9-837e-4adef2eff2fa	8f790b97-9843-5efe-b338-e1c37618c616	KATAKANA	テニス
567ed735-4a66-5e94-bbc8-5090fdf1d0b8	5ca8a07b-ac34-5789-ab4a-8bd29d51929c	KATAKANA	テレビ
611ffb13-c0de-5e82-b9e8-8e6aef023163	b3e732b3-61c4-574d-8901-b1c74076b5ea	KATAKANA	アイスクリーム
5a316e7e-9528-5378-b9bd-dc9cbb9ea94a	720a22a5-3132-56e6-8380-03a20e2a4af1	KATAKANA	ハンバーガー
ef2f1e4a-366a-5f14-a6af-c6e6a502181b	dbf81e7a-c846-5129-b017-a31ad90eb752	KANJI	お酒
27b33027-745c-55df-a82d-401f64d84976	dbf81e7a-c846-5129-b017-a31ad90eb752	HIRAGANA	おさけ
d86c9f10-796e-53aa-897a-9299e71f95e0	4b6153f0-1c2e-5330-94dc-e71afa17d386	KANJI	お茶
186d373a-af22-5e74-ab8d-bfa5dc640366	4b6153f0-1c2e-5330-94dc-e71afa17d386	HIRAGANA	おちゃ
a110bba0-64d5-5aff-8ff6-db02a4456030	c675acf7-4ab1-5a0a-beb3-e65aff83932a	KATAKANA	コーヒー
1c628527-0ac5-5e63-8b2d-76d93e27ecb1	ff69f94e-49fa-5015-a8e8-6bdab429f087	KANJI	水
d59f0854-f076-5966-bb98-0fb14f7d18a5	ff69f94e-49fa-5015-a8e8-6bdab429f087	HIRAGANA	みず
cf081311-5a4c-548e-b917-e4921e8a5b6f	455c3d21-e4c5-5cae-a4b1-2f4a0b988e50	KANJI	朝ご飯
7406b2c1-bea9-513c-9140-87823395b10e	455c3d21-e4c5-5cae-a4b1-2f4a0b988e50	HIRAGANA	あさごはん
480494bb-7ade-5ed2-b086-62456da3bfed	43903683-a322-530c-88e1-a1c406296be6	KANJI	昼ご飯
da647682-f600-5815-93b4-d006ca201e96	43903683-a322-530c-88e1-a1c406296be6	HIRAGANA	ひるごはん
caa2b9cb-99f6-532f-b576-e192e9bbb901	4e1fc6f4-772d-5a1f-8391-5453bcbf4817	KANJI	晩御飯
c6e4670c-d1fd-57a9-9672-f0bbc53f3245	4e1fc6f4-772d-5a1f-8391-5453bcbf4817	HIRAGANA	ばんごはん
f04da983-97fa-572a-afb4-274d6295a907	e6aa13b6-6f6a-57db-8433-701ba55fa918	KANJI	家
b4f062fe-d959-5334-9aab-c185955f0bc9	e6aa13b6-6f6a-57db-8433-701ba55fa918	HIRAGANA	いえ
2df7adb4-7be3-54f1-a17e-70d199ab1435	522046f3-2cfa-565e-a8d5-15f048b49776	HIRAGANA	うち
7e57285a-599a-51a8-a3f6-91613f0c8eb1	522046f3-2cfa-565e-a8d5-15f048b49776	KANJI	家
f76ccfc2-e037-53eb-b589-84b398ac85bc	c5ebd7d9-06b2-596e-96f5-7366810ea78c	KATAKANA	カフェ
68904bd3-8910-5e50-af0b-18a5e6e17fde	3972d239-6e9a-5163-bf43-c4a2b81783db	KANJI	明日
6f970b2c-2759-51ae-b7d1-fb3831b53d96	3972d239-6e9a-5163-bf43-c4a2b81783db	HIRAGANA	あした
3f0ebf47-15c2-5419-831e-a2d9d2a3d7d7	a4dbf3ac-a372-58a7-b9ac-a7f018ba587c	KANJI	今日
3b1af04a-9a5e-56a1-8ce7-664f74179aa8	a4dbf3ac-a372-58a7-b9ac-a7f018ba587c	HIRAGANA	きょう
943d1186-49af-5019-aaf7-845aa4a64b66	b415c8ff-d8fb-5e40-9a89-ed5fa1997831	KANJI	朝
022cfee4-a9d3-5585-bd68-5a9148004557	b415c8ff-d8fb-5e40-9a89-ed5fa1997831	HIRAGANA	あさ
c9871de1-bc76-5fc0-aec0-406bd3745afb	e872c853-3159-56b7-834e-066e364a5782	KANJI	今晩
8cc981ca-b491-5c68-9a56-18294c5743ac	e872c853-3159-56b7-834e-066e364a5782	HIRAGANA	こんばん
45b43f20-7c24-5f2b-ab1b-b8dbb4f7356e	7ec8469c-ee0f-5a33-9f9f-e025e6cffdd0	KANJI	毎日
a374e696-c3e5-56a9-a24b-455729f9e734	7ec8469c-ee0f-5a33-9f9f-e025e6cffdd0	HIRAGANA	まいにち
978e7bb1-c35a-5d00-8c7b-5be1235be5fd	1efcd811-18f8-5b64-be29-9f46fbef3286	KANJI	毎朝
732b45e3-1b38-521c-a719-da5599a8710d	1efcd811-18f8-5b64-be29-9f46fbef3286	HIRAGANA	まいあさ
ef94c592-242b-5cab-8195-86144bf828da	9e1ccd85-e76b-559b-9c13-ac2202a04849	KANJI	毎晩
ca2ead6a-5c21-569f-9c5f-f2b4fb4d7bc1	9e1ccd85-e76b-559b-9c13-ac2202a04849	HIRAGANA	まいばん
a5284a6a-7a00-5315-b6ff-6e4c162df689	788e64aa-46ee-574d-aad9-83c8b56bc40a	KANJI	週末
502dcd13-6ace-54d3-b94d-0cf6581ffdac	788e64aa-46ee-574d-aad9-83c8b56bc40a	HIRAGANA	しゅうまつ
b7d10589-eb26-5aa7-9414-f67ec5c70a70	f7d20fe3-57b3-5e6d-85f6-5b54b0ba7088	KANJI	土曜日
13c8c94b-4e46-580a-8f5e-f0320a39b319	61637bb4-cc4a-52be-86fa-7fb678ab6e62	KANJI	日曜日
c0954054-5887-5265-96b1-cd445bad7358	61637bb4-cc4a-52be-86fa-7fb678ab6e62	HIRAGANA	にちようび
24a8a0dd-b581-57a3-a312-50b0e44d8cf5	f05e2adf-83c4-5ccf-a74a-fbd5664dceb1	HIRAGANA	いつ
fae34bc4-96e0-5534-bfc0-d4e59766cff8	50cbf96a-57d3-5f45-b5fc-e415ff57717a	KANJI	行く
6b9521d3-8f71-5429-b4e2-5ddbcfde5313	50cbf96a-57d3-5f45-b5fc-e415ff57717a	HIRAGANA	いく
48eb2d04-e25e-5ec9-825f-19ea38803db1	243bdf44-b101-5d5d-bc51-a4f5c1c03bc3	KANJI	帰る
c1766267-1a80-5742-bb98-d2ae8515e448	243bdf44-b101-5d5d-bc51-a4f5c1c03bc3	HIRAGANA	かえる
669a71d0-7288-5f83-b66d-ba9c40e2188f	27c14160-d0d6-51f8-b7b1-9172cf8b84d2	KANJI	聞く
ed04ab7a-ebf5-5b32-8039-80f0724eacb8	27c14160-d0d6-51f8-b7b1-9172cf8b84d2	HIRAGANA	きく
f228a714-c3b3-553e-87c6-ebc85e40c6ba	b8a9c502-6f50-5d50-96f1-417feef69161	KANJI	飲む
c96dfbe4-4eac-55d9-8106-096eab5a26cc	b8a9c502-6f50-5d50-96f1-417feef69161	HIRAGANA	のむ
2e51a478-a7e4-554e-b12a-16f37d55a888	87e72b5b-0332-5b61-b21c-ea8cc6a05c1f	KANJI	話す
17e1d10b-47f4-5929-be2e-da136c537152	87e72b5b-0332-5b61-b21c-ea8cc6a05c1f	HIRAGANA	はなす
5c4eedcf-4c9c-548c-a00c-de9261275d44	615aadf7-6900-5c48-814a-d6cc0884ae86	KANJI	読む
359d7060-7f35-5f8f-acf2-476fd99adb02	615aadf7-6900-5c48-814a-d6cc0884ae86	HIRAGANA	よむ
9938b5a9-495e-5825-a10c-fa2848dc9fe7	bd0e8ffa-f7cd-5b27-8481-6ecb87512f6c	KANJI	起きる
499c4e39-aa7c-59e4-a742-4bc752975f2e	bd0e8ffa-f7cd-5b27-8481-6ecb87512f6c	HIRAGANA	おきる
bc763c1e-d3ba-520d-a994-d8dbd8a878a3	28166f6b-3aba-5230-9bd4-18e1a467bc3f	KANJI	食べる
d6f65dc3-3834-57af-9912-cdf8928904fd	28166f6b-3aba-5230-9bd4-18e1a467bc3f	HIRAGANA	たべる
a44f4e44-20c5-5c46-ab3a-c78352c643f6	45b4593b-f7a0-516e-84c4-68c7a7099243	KANJI	寝る
5921a394-3b72-55da-a798-efde8d70ba24	45b4593b-f7a0-516e-84c4-68c7a7099243	HIRAGANA	ねる
ad9d8a4a-5776-5918-9cd7-ab617ded64c8	66e4cf05-72cd-512b-a462-05f4e69906b5	KANJI	見る
c17eaf30-53db-518e-86d6-e91d7702933d	66e4cf05-72cd-512b-a462-05f4e69906b5	HIRAGANA	みる
bdd8a736-404d-5b67-9dcc-be354e0725e5	10567940-e3bb-5257-b33c-ae9976bfaddd	KANJI	来る
81c35e68-258b-52fe-b85c-76be5dd16fc1	10567940-e3bb-5257-b33c-ae9976bfaddd	HIRAGANA	くる
c32694ad-c718-54cc-9465-256f9d59d0f8	5800b221-4eb5-5331-8c6c-413e2d08c007	HIRAGANA	する
0023dc73-8fa0-58aa-8fba-2bd1bcf343fe	9e9dc42e-df1e-5ace-948a-9dce72caaceb	KANJI	勉強する
b222cdbd-182c-5b8e-b00c-93e2bcaf492e	9e9dc42e-df1e-5ace-948a-9dce72caaceb	HIRAGANA	べんきょうする
ea974989-8c3e-5a37-a38b-54463cd75e2f	349836a3-2523-5f85-a7c1-f85e49c73f27	HIRAGANA	いい
daa72300-c1e3-50c7-aa04-fd0ae580966d	fc5427a3-a54b-5c04-8b36-a570b8e7a72b	KANJI	早い
343b9649-76d5-5277-b024-a71f21e10de9	fc5427a3-a54b-5c04-8b36-a570b8e7a72b	HIRAGANA	はやい
0ae8bfe8-5aed-5bc1-b505-0eed73a461c8	311f8bec-e54c-5317-8880-dfd3e39d23a7	HIRAGANA	あまり + negative
aa2b1deb-a8b5-55c4-884b-496235ad9819	1e68ea18-9d69-5761-a463-510eab20d1d3	KANJI	全然 + negative
42eb00c6-5af8-516c-90ee-a3f7a2134d57	1e68ea18-9d69-5761-a463-510eab20d1d3	HIRAGANA	ぜんぜん
95963f3b-74ec-578b-9077-2c457fdef5d1	818210e0-641a-56f3-9241-7d24504480e2	HIRAGANA	たいてい
3cdcdc20-a6bf-5a81-9962-5d15ab450859	818210e0-641a-56f3-9241-7d24504480e2	KANJI	大抵
c8550df0-3aeb-5cc3-bc1a-dc4cb78b4528	b8b492d0-5612-5ac1-bb4d-15f03e959c89	HIRAGANA	ちょっと
64f9e91f-e687-5062-b259-bcf29d476066	02115b33-cd19-587a-a3f2-446dea254e33	KANJI	時々
69f55c60-055a-545d-9f80-aca4f5e151c8	02115b33-cd19-587a-a3f2-446dea254e33	HIRAGANA	ときどき
5d59387c-87a8-5428-952f-1762e0e224cd	d7dc5e63-a1ca-58e6-9c31-047e23342953	HIRAGANA	よく
23aed903-bbee-5860-bb46-0625fcc3a740	31df08c1-4538-5a5f-aa61-af9e88805bd5	HIRAGANA	そうですね
d9c81727-6c65-526f-9592-e3d0c3d4ccee	7d85fe8c-2226-573f-a219-0f2def303b50	HIRAGANA	でも
2eedf85e-4cb8-54e6-a3da-6bea09e2c6dd	4fe504e0-3bf5-5480-98e6-5e626dddc417	HIRAGANA	どうてすか
da051662-bc91-5d51-99e8-2a60f04b04ae	6e231f76-5724-5b3c-acfc-cdee6e2eb3a6	HIRAGANA	ええ
3f28b04b-aee2-5c5b-a69e-75c4c0bb906e	50ec881b-73e0-502b-8316-3d67e8dababb	HIRAGANA	みんな
f80185d3-81be-51ea-96ef-583303d320e5	f5f775ad-523f-5888-b269-fcb13c1c1051	KANJI	大丈夫
44569812-2095-5367-8e63-16b0f744a24c	f5f775ad-523f-5888-b269-fcb13c1c1051	HIRAGANA	だいじょうぶ
9444575e-54af-5498-b338-1d0a757e5646	6ae314ed-22ae-5a5f-8e43-c5165e6eb3a1	KANJI	大丈夫です
6e3adf8f-f6c5-5b52-a838-b75406838c9d	6ae314ed-22ae-5a5f-8e43-c5165e6eb3a1	HIRAGANA	だいじょうぶです
67008121-6a9d-520f-a4b1-a64d08a2f9fc	8640d0ab-1efc-5b18-a49c-564f8e53d65c	KANJI	少し
070d5515-2b7c-5dc3-8a51-353fcd4a887c	8640d0ab-1efc-5b18-a49c-564f8e53d65c	HIRAGANA	すこし
\.


--
-- Data for Name: spelling_kind; Type: TABLE DATA; Schema: vocabulary; Owner: postgres
--

COPY vocabulary.spelling_kind (code, label) FROM stdin;
HIRAGANA	Hiragana
KATAKANA	Katakana
KANJI	Kanji
\.


--
-- Data for Name: vocabulary; Type: TABLE DATA; Schema: vocabulary; Owner: postgres
--

COPY vocabulary.vocabulary (id, word_class_code, conjugation_kind_code, jlpt_level, row_num, tags, date_added) FROM stdin;
8f790b97-9843-5efe-b338-e1c37618c616	NOUN	\N	\N	172	{entertainment}	2022-07-24
5ca8a07b-ac34-5789-ab4a-8bd29d51929c	NOUN	\N	\N	173	{entertainment}	2022-07-24
b3e732b3-61c4-574d-8901-b1c74076b5ea	NOUN	\N	\N	174	{food}	2022-07-24
720a22a5-3132-56e6-8380-03a20e2a4af1	NOUN	\N	\N	175	{food}	2022-07-24
dbf81e7a-c846-5129-b017-a31ad90eb752	NOUN	\N	\N	176	{food}	2022-07-24
4b6153f0-1c2e-5330-94dc-e71afa17d386	NOUN	\N	\N	177	{food}	2022-07-24
c675acf7-4ab1-5a0a-beb3-e65aff83932a	NOUN	\N	\N	178	{food}	2022-07-24
ff69f94e-49fa-5015-a8e8-6bdab429f087	NOUN	\N	\N	179	{food}	2022-07-24
455c3d21-e4c5-5cae-a4b1-2f4a0b988e50	NOUN	\N	\N	180	{food}	2022-07-24
43903683-a322-530c-88e1-a1c406296be6	NOUN	\N	\N	181	{food}	2022-07-24
4e1fc6f4-772d-5a1f-8391-5453bcbf4817	NOUN	\N	\N	182	{food}	2022-07-24
e6aa13b6-6f6a-57db-8433-701ba55fa918	NOUN	\N	\N	183	{place}	2022-07-24
522046f3-2cfa-565e-a8d5-15f048b49776	NOUN	\N	\N	184	{place}	2022-07-24
c5ebd7d9-06b2-596e-96f5-7366810ea78c	NOUN	\N	\N	185	{place}	2022-07-24
3972d239-6e9a-5163-bf43-c4a2b81783db	NOUN	\N	\N	186	{time}	2022-07-24
a4dbf3ac-a372-58a7-b9ac-a7f018ba587c	NOUN	\N	\N	187	{time}	2022-07-24
b415c8ff-d8fb-5e40-9a89-ed5fa1997831	NOUN	\N	\N	188	{time}	2022-07-24
e872c853-3159-56b7-834e-066e364a5782	NOUN	\N	\N	189	{time}	2022-07-24
7ec8469c-ee0f-5a33-9f9f-e025e6cffdd0	NOUN	\N	\N	190	{time}	2022-07-24
1efcd811-18f8-5b64-be29-9f46fbef3286	NOUN	\N	\N	191	{time}	2022-07-24
9e1ccd85-e76b-559b-9c13-ac2202a04849	NOUN	\N	\N	192	{time}	2022-07-24
788e64aa-46ee-574d-aad9-83c8b56bc40a	NOUN	\N	\N	193	{time}	2022-07-24
f7d20fe3-57b3-5e6d-85f6-5b54b0ba7088	NOUN	\N	\N	194	{time}	2022-07-24
61637bb4-cc4a-52be-86fa-7fb678ab6e62	NOUN	\N	\N	195	{time}	2022-07-24
f05e2adf-83c4-5ccf-a74a-fbd5664dceb1	NOUN	\N	\N	196	{time}	2022-07-24
50cbf96a-57d3-5f45-b5fc-e415ff57717a	VERB	GODAN_VERB	\N	197	{}	2022-07-24
243bdf44-b101-5d5d-bc51-a4f5c1c03bc3	VERB	GODAN_VERB	\N	198	{}	2022-07-24
27c14160-d0d6-51f8-b7b1-9172cf8b84d2	VERB	GODAN_VERB	\N	199	{}	2022-07-24
b8a9c502-6f50-5d50-96f1-417feef69161	VERB	GODAN_VERB	\N	200	{}	2022-07-24
87e72b5b-0332-5b61-b21c-ea8cc6a05c1f	VERB	GODAN_VERB	\N	201	{}	2022-07-24
615aadf7-6900-5c48-814a-d6cc0884ae86	VERB	GODAN_VERB	\N	202	{}	2022-07-24
bd0e8ffa-f7cd-5b27-8481-6ecb87512f6c	VERB	ICHIDAN_VERB	\N	203	{}	2022-07-24
28166f6b-3aba-5230-9bd4-18e1a467bc3f	VERB	ICHIDAN_VERB	\N	204	{}	2022-07-24
45b4593b-f7a0-516e-84c4-68c7a7099243	VERB	ICHIDAN_VERB	\N	205	{}	2022-07-24
66e4cf05-72cd-512b-a462-05f4e69906b5	VERB	ICHIDAN_VERB	\N	206	{}	2022-07-24
10567940-e3bb-5257-b33c-ae9976bfaddd	VERB	IRREGULAR_VERB	\N	207	{}	2022-07-24
5800b221-4eb5-5331-8c6c-413e2d08c007	VERB	IRREGULAR_VERB	\N	208	{}	2022-07-24
9e9dc42e-df1e-5ace-948a-9dce72caaceb	VERB	IRREGULAR_VERB	\N	209	{}	2022-07-24
349836a3-2523-5f85-a7c1-f85e49c73f27	ADJECTIVE	I_ADJECTIVE	\N	210	{}	2022-07-24
fc5427a3-a54b-5c04-8b36-a570b8e7a72b	ADJECTIVE	I_ADJECTIVE	\N	211	{}	2022-07-24
311f8bec-e54c-5317-8880-dfd3e39d23a7	ADVERB	\N	\N	212	{}	2022-07-24
1e68ea18-9d69-5761-a463-510eab20d1d3	ADVERB	\N	\N	213	{}	2022-07-24
818210e0-641a-56f3-9241-7d24504480e2	ADVERB	\N	\N	214	{}	2022-07-24
b8b492d0-5612-5ac1-bb4d-15f03e959c89	ADVERB	\N	\N	215	{}	2022-07-24
02115b33-cd19-587a-a3f2-446dea254e33	ADVERB	\N	\N	216	{}	2022-07-24
d7dc5e63-a1ca-58e6-9c31-047e23342953	ADVERB	\N	\N	217	{}	2022-07-24
31df08c1-4538-5a5f-aa61-af9e88805bd5	EXPRESSION	\N	\N	218	{}	2022-07-24
7d85fe8c-2226-573f-a219-0f2def303b50	EXPRESSION	\N	\N	219	{}	2022-07-24
4fe504e0-3bf5-5480-98e6-5e626dddc417	EXPRESSION	\N	\N	220	{}	2022-07-24
6e231f76-5724-5b3c-acfc-cdee6e2eb3a6	EXPRESSION	\N	\N	221	{}	2022-07-24
50ec881b-73e0-502b-8316-3d67e8dababb	NOUN	\N	\N	222	{}	2022-07-24
f5f775ad-523f-5888-b269-fcb13c1c1051	EXPRESSION	\N	\N	223	{}	2022-07-24
6ae314ed-22ae-5a5f-8e43-c5165e6eb3a1	EXPRESSION	\N	\N	224	{}	2022-07-24
8640d0ab-1efc-5b18-a49c-564f8e53d65c	ADVERB	\N	\N	225	{}	2022-07-24
8359d7d1-d6d6-5995-962e-3f6566232911	EXPRESSION	\N	\N	2	{greeting}	2022-04-24
0f06fb63-c508-57a2-8983-2cb9f5dfd2e7	NOUN	\N	\N	164	{number}	2022-07-24
662aaf34-2fd3-5ab7-ad40-ee6e4862fc2c	NOUN	\N	\N	165	{number}	2022-07-24
06eedaf7-2c40-54e4-a1ea-669b265fda8d	NOUN	\N	\N	166	{number}	2022-07-24
b82fa253-fbb7-559c-b127-77d2262df800	NOUN	\N	\N	167	{entertainment}	2022-07-24
aa05a718-c2a5-5083-9c3d-b0e4d1bd09a8	NOUN	\N	\N	168	{entertainment}	2022-07-24
3bef2c81-886b-5bbd-9f1b-dfc94288bea2	NOUN	\N	\N	169	{entertainment}	2022-07-24
bbf5a302-a23c-5142-b721-2aa90ff1e8dc	NOUN	\N	\N	170	{entertainment}	2022-07-24
738002f3-36da-5668-8a8b-46415de8cd52	NOUN	\N	\N	171	{entertainment}	2022-07-24
58ecba76-692b-5baf-a096-62bf3e752da2	PRONOUN	\N	\N	100	{pointing_word}	2022-05-30
8373ee97-fb02-5fa4-9f3a-e6d0bfe42042	PRONOUN	\N	\N	101	{pointing_word}	2022-05-30
e3286acb-a31b-5fb6-9c16-28ac16fcc269	PRONOUN	\N	\N	102	{pointing_word}	2022-05-30
9ef8e062-db86-5059-82be-ccc85580d5e7	PRONOUN	\N	\N	103	{pointing_word}	2022-05-30
430b4ea9-6092-52c7-b2bf-4300de6196c7	PRONOUN	\N	\N	104	{pointing_word}	2022-05-30
9d9f54be-2a19-5147-8935-e7c3c887fa03	PRONOUN	\N	\N	105	{pointing_word}	2022-05-30
908efe1a-b6e9-55d5-b550-461cea175af4	PRONOUN	\N	\N	106	{pointing_word}	2022-05-30
a9b7cffc-2d14-58ae-818b-3ac3b6e6e4d0	PRONOUN	\N	\N	107	{pointing_word}	2022-05-30
9c591bfa-c4a7-5263-b2b1-7c8d14e10c34	PRONOUN	\N	\N	108	{pointing_word}	2022-05-30
0279527b-c398-5856-aad3-11b834907f56	PRONOUN	\N	\N	109	{pointing_word}	2022-05-30
5587745e-e524-5159-8fe6-1e8637b90455	PRONOUN	\N	\N	110	{pointing_word}	2022-05-30
1502a2cb-059c-5a9f-baab-af68a0d09ac8	PRONOUN	\N	\N	111	{pointing_word}	2022-05-30
f7aa023d-a413-562b-bf45-75367462a4b2	NOUN	\N	\N	115	{food}	2022-05-30
4c55fd59-1160-52d2-8383-00f292668c1f	EXPRESSION	\N	\N	3	{greeting}	2022-04-24
8d46e404-38ff-518c-a25d-cb95d6f2c511	EXPRESSION	\N	\N	4	{greeting}	2022-04-24
1184380a-27db-5f8e-841d-7b105c2a7366	EXPRESSION	\N	\N	5	{greeting}	2022-04-24
3ecfb07b-0a66-555c-97c4-5af7cb806507	EXPRESSION	\N	\N	6	{greeting}	2022-04-24
a419bb62-f061-5ac6-856b-6f85002642c6	EXPRESSION	\N	\N	7	{greeting}	2022-04-24
c7a645f7-256a-536a-a5ee-5f85e8ce1137	EXPRESSION	\N	\N	8	{greeting}	2022-04-24
b626308d-6418-5a2c-9151-7a8a368f27a3	EXPRESSION	\N	\N	9	{greeting}	2022-04-24
1913dbc6-4712-549a-9274-dc3511cecbfe	EXPRESSION	\N	\N	10	{greeting}	2022-04-24
e7161fcf-fbaf-5971-8adb-70745ae24103	EXPRESSION	\N	\N	11	{greeting}	2022-04-24
c2a4dbcf-dba1-5e27-964f-5e2f7b358725	EXPRESSION	\N	\N	12	{greeting}	2022-04-24
b06abb09-c9bd-5beb-a7b7-d184bedf72ca	EXPRESSION	\N	\N	13	{greeting}	2022-04-24
47b08527-885b-50bb-b538-180dc308199a	EXPRESSION	\N	\N	14	{greeting}	2022-04-24
797b03e3-1495-52c0-baf4-ffb9cb324089	EXPRESSION	\N	\N	15	{greeting}	2022-04-24
59bad243-8b31-54f3-9d7b-6de0961bc059	EXPRESSION	\N	\N	16	{greeting}	2022-04-24
05436324-1858-5a2f-a2a1-a41d00f862cc	EXPRESSION	\N	\N	17	{greeting}	2022-04-24
136d6d6d-0487-5709-ac27-2c376bfebd43	EXPRESSION	\N	\N	18	{greeting}	2022-04-24
ddd5fd1f-f041-576c-a479-d8d9a3221370	EXPRESSION	\N	\N	19	{greeting}	2022-04-24
d43cc787-37af-5dd1-92b5-fc756f18298a	NOUN	\N	\N	20	{school,place}	2022-04-24
3c890a54-15f9-56e1-9bd5-3c91274b6246	NOUN	\N	\N	21	{school,place}	2022-04-24
6de86986-05ed-58da-a055-9d49c89f7a06	NOUN	\N	\N	22	{school}	2022-04-24
d1cb9e5b-6cc5-5339-b33d-4e948229156e	NOUN	\N	\N	23	{school}	2022-04-24
efe5fea4-0476-5328-bd18-2a469a8b6c3e	NOUN	\N	\N	24	{school}	2022-04-24
c21b42a5-1ef1-51cf-9402-199d2b562a47	NOUN	\N	\N	25	{school}	2022-04-24
057a7cd1-f1a6-50e2-8449-3b6f04c67305	NOUN	\N	\N	26	{school}	2022-04-24
e71aac30-c949-5b47-b118-d02a5d703701	NOUN	\N	\N	27	{school}	2022-04-24
4b1d8b7f-6477-5881-9439-1a7f30b26c9d	NOUN	\N	\N	28	{person}	2022-04-24
f65b93fb-1086-5405-8163-394db3e83014	NOUN	\N	\N	29	{person}	2022-04-24
c26e8356-f73a-5ddf-b045-432580c37468	NOUN	\N	\N	30	{person}	2022-04-24
0ef6972f-5e7d-5b51-be7f-8f39a5fd5e0e	NOUN	\N	\N	31	{person}	2022-04-24
146b7705-d9cd-511f-988c-5b2cc7661b75	ADVERB	\N	\N	32	{}	2022-04-24
8bedfb60-1599-58b0-9568-5e72b8cf6b7e	NOUN	\N	\N	33	{place}	2022-04-24
25f93b07-dc04-553a-8794-329cc907dbe5	NOUN	\N	\N	34	{school,place}	2022-04-24
37eba762-70cf-5c5c-b689-621f26c1ee5f	NOUN	\N	\N	35	{school,place}	2022-04-24
f552ea3a-8b7f-5433-aa20-4c93a6868fb3	EXPRESSION	\N	\N	1	{greeting}	2022-04-24
e7323b54-9fa5-5cb7-8d68-d2ebe9b379ea	NOUN	\N	\N	132	{things}	2022-05-30
99cdf9f9-df8c-5d7a-9ea1-a7c003c9f289	NOUN	\N	\N	36	{school,place}	2022-04-24
947e6f87-e4fe-5b27-9bd3-215e8b1fc71a	NOUN	\N	\N	37	{person}	2022-04-24
dd191fac-e29b-5262-b2a6-f9f2875a4a3f	VERB	GODAN_VERB	\N	38	{}	2022-04-24
fc82f2ac-3b17-582f-9259-7200e1430824	PROPER_NOUN	\N	\N	39	{place,city}	2022-04-24
15c5fee6-dda7-5d40-b7fd-382d97a28b9c	PROPER_NOUN	\N	\N	40	{place,city}	2022-04-24
39458010-a5eb-5b13-b054-4cc458702c56	PROPER_NOUN	\N	\N	41	{place,city}	2022-04-24
091d6d9c-5d19-51f5-bc28-3b5281042464	VERB	GODAN_VERB	\N	42	{}	2022-04-24
6e44df4a-4366-5c1a-86af-87a4e5f61fc6	EXPRESSION	\N	\N	43	{}	2022-04-24
7748dd60-2126-5e30-a5b6-39596a0e25e4	EXPRESSION	\N	\N	44	{}	2022-04-24
dce3fe6a-497c-54b2-a121-df6767084258	NOUN	\N	\N	45	{time}	2022-04-24
42ffdbae-c27d-53a4-b113-95583953e16c	NOUN	\N	\N	46	{time}	2022-04-24
94611381-0488-58aa-8cf2-12ad9ff4e2dd	NOUN	\N	\N	47	{time}	2022-04-24
40502e18-b4ba-50b4-89b7-23fd3b4d4a21	NOUN	\N	\N	48	{time}	2022-04-24
5372b023-b803-551c-9a7f-f404804f63f1	NOUN	\N	\N	49	{time}	2022-04-24
2cf1d32f-b261-5ba6-829c-a9f1237a88d6	NOUN	\N	\N	50	{time}	2022-04-24
a39e794c-9682-5cd5-9c5e-dd961aa92bc5	NOUN	\N	\N	51	{time}	2022-04-24
9e67a677-9058-536e-85d6-389ea5138c86	NOUN	\N	\N	52	{time}	2022-04-24
252852aa-aefd-504b-b671-668a33020492	NOUN	\N	\N	53	{number,time}	2022-04-24
cc6a4913-6f39-521b-a0cf-9631f640800e	PROPER_NOUN	\N	\N	54	{place,country}	2022-04-24
522e0796-297d-5bf2-be85-e93596baa6bf	PROPER_NOUN	\N	\N	55	{place,country}	2022-04-24
a420a37b-593e-572e-aae7-d724eca6803d	NOUN	\N	\N	56	{}	2022-04-24
c59169cb-e5ad-5311-a9ce-43764a5ddda6	NOUN	\N	\N	57	{}	2022-04-24
1731f988-dcfb-5b31-bda9-c8988a5711bf	NOUN	\N	\N	58	{}	2022-04-24
f6524cfb-fa66-56ae-ae4c-0e38a8b6e54c	NOUN	\N	\N	59	{}	2022-04-24
d8f98686-282e-5c5f-88b2-4e046e707e54	NOUN	\N	\N	60	{}	2022-04-24
728f5a87-3848-5450-a941-f9cd0f898067	NOUN	\N	\N	61	{}	2022-04-24
507092ff-6dc3-5749-a9c3-79ba8d2bc360	INTERJECTION	\N	\N	62	{}	2022-04-24
84e857b9-5605-51fb-bfb3-8cc08eb579d3	EXPRESSION	\N	\N	63	{}	2022-04-24
dde2be42-c934-53fc-9dc6-3ce12b727924	INTERJECTION	\N	\N	64	{}	2022-04-24
0428342a-6b57-5e6f-b1c6-4aa6ccfdb267	INTERJECTION	\N	\N	65	{}	2022-04-24
a48cfff2-551b-5525-bf29-f706571e7e15	EXPRESSION	\N	\N	66	{}	2022-04-24
311cf3c0-5798-551b-a933-eb33c4ce7843	EXPRESSION	\N	\N	67	{}	2022-04-24
40483fe8-7fda-56b7-b28b-eedb9da50a52	PROPER_NOUN	\N	\N	68	{place,country}	2022-04-24
2a0ff39c-471b-56d1-a2b2-39f20596f70c	PROPER_NOUN	\N	\N	69	{place,country}	2022-04-24
5cb08712-5c04-56f0-aaf6-a8e76a1e0ef4	PROPER_NOUN	\N	\N	70	{place,country}	2022-04-24
b4a146e5-f483-50d5-8b18-ca71fdb3f570	PROPER_NOUN	\N	\N	71	{place,country}	2022-04-24
54ca6cf2-c9f0-5a77-b53c-8e4a2fbe76ac	PROPER_NOUN	\N	\N	72	{place,country}	2022-04-24
f3f3ba33-de3b-5fa1-9b99-eee62ae2e024	PROPER_NOUN	\N	\N	73	{place,country}	2022-04-24
eb20360e-5c1a-58b8-9bf8-040795c5bf0a	PROPER_NOUN	\N	\N	74	{place,country}	2022-04-24
ae144a7c-293f-5f24-81ee-37d5594d6aed	PROPER_NOUN	\N	\N	75	{place,country}	2022-04-24
abd1e50d-f284-5a21-831f-344cb338414e	NOUN	\N	\N	76	{school_major}	2022-04-24
13912d04-d947-5c72-a192-740c19a28c86	NOUN	\N	\N	77	{school_major}	2022-04-24
7672ef18-d91f-548f-a938-970d6a741586	NOUN	\N	\N	78	{school_major}	2022-04-24
471eeab4-7852-53f9-ba6c-342f13746958	NOUN	\N	\N	79	{school_major}	2022-04-24
9e62feab-62dc-5faf-92d9-8cd423af7dab	NOUN	\N	\N	80	{school_major}	2022-04-24
15b8fb86-6025-524c-a968-0d8e442c0d8e	NOUN	\N	\N	81	{school_major}	2022-04-24
ee82d23f-991e-59ba-9646-d407d35b46ae	NOUN	\N	\N	82	{school_major}	2022-04-24
a74f0164-da3d-59dc-bbd0-39cf065ee813	NOUN	\N	\N	83	{school_major}	2022-04-24
f41b3958-7c0c-5c05-9ea8-d0099b014f0c	NOUN	\N	\N	84	{school_major}	2022-04-24
0d91d253-e394-572c-a6c4-37e20ed0f603	NOUN	\N	\N	85	{school_major}	2022-04-24
a9d318d5-97e8-5990-916c-fd300d951e2c	NOUN	\N	\N	86	{school_major}	2022-04-24
45983eaf-745d-5a47-bd34-aa09444736a4	NOUN	\N	\N	87	{occupation}	2022-04-24
b01b2de0-f80a-587a-99bb-8e7e2157feec	NOUN	\N	\N	88	{occupation}	2022-04-24
a2bb0866-9ce3-5b9a-bc60-49b48a40e40f	NOUN	\N	\N	89	{occupation}	2022-04-24
c511ec54-4f00-5426-89ef-ee1558ccfb60	NOUN	\N	\N	90	{occupation}	2022-04-24
7d3f6d4b-fd98-5a19-ba56-b7c707092305	NOUN	\N	\N	91	{occupation}	2022-04-24
98e48ea2-8566-55a1-bfb8-d5aaf1a060d0	NOUN	\N	\N	92	{occupation}	2022-04-24
902b7ccb-091c-502b-a413-5a3a38c655a4	NOUN	\N	\N	93	{occupation}	2022-04-24
f8d03abe-255a-5717-811a-91f511c39759	NOUN	\N	\N	94	{family}	2022-04-24
d7a063c8-998c-50a3-825c-86f896a5a0c7	NOUN	\N	\N	95	{family}	2022-04-24
e09e6607-0754-53e7-ac90-7d5eec6a3442	NOUN	\N	\N	96	{family}	2022-04-24
942ce296-4fdd-5a7d-9c2c-53d4c2ae2de7	NOUN	\N	\N	97	{family}	2022-04-24
d4387364-3f1c-51be-9762-ddf41f844fb7	NOUN	\N	\N	98	{family}	2022-04-24
acc5e753-294b-571f-9ee7-a7cd6eeea235	NOUN	\N	\N	99	{family}	2022-04-24
93eaf294-a292-5980-b17e-de43755335c9	PRONOUN	\N	\N	112	{pointing_word}	2022-05-30
f5fce96d-2982-5d7a-b7ce-9dc8914c6ab3	ADJECTIVE	I_ADJECTIVE	\N	113	{food}	2022-05-30
10a5183d-9b5d-58c7-885f-0210e56c1dca	ADJECTIVE	I_ADJECTIVE	\N	114	{food}	2022-05-30
81b8f353-cd1c-53e4-a0f4-1630c490c959	NOUN	\N	\N	116	{food}	2022-05-30
e2c0e2f3-891a-55e3-ad28-285669d27287	NOUN	\N	\N	117	{food}	2022-05-30
2dd4acb2-11ca-50c5-b365-c22616ea316d	NOUN	\N	\N	118	{food}	2022-05-30
3ab19c55-4c6a-53e0-afce-41968363d92a	NOUN	\N	\N	119	{food}	2022-05-30
496222cc-1cb3-50c8-9e7e-bbc7d327b78c	NOUN	\N	\N	120	{food}	2022-05-30
4fbc0a01-e32a-5449-8efe-3ae37aee4f2f	NOUN	\N	\N	121	{food}	2022-05-30
b74ca7fa-4622-5a52-b9df-5260878e687b	NOUN	\N	\N	122	{food}	2022-05-30
5e2b2d0d-7461-56e2-a151-9a10375d5615	NOUN	\N	\N	123	{food}	2022-05-30
12c11165-7996-5b13-bf19-c3afc419afb2	NOUN	\N	\N	124	{food}	2022-05-30
b1852be8-7a0e-5541-b94d-29be625cb423	NOUN	\N	\N	125	{things}	2022-05-30
b5d661b7-f275-5647-a86b-cf4bd963f512	NOUN	\N	\N	126	{things}	2022-05-30
96fb26f0-20df-51e0-b1c4-793ab736c2d9	NOUN	\N	\N	127	{things}	2022-05-30
b802b5a4-5804-5f5a-a193-e475bceaf5dd	NOUN	\N	\N	128	{things}	2022-05-30
bf5e0762-effe-5d38-8786-503cde98e504	NOUN	\N	\N	129	{things}	2022-05-30
04bddd51-0749-5efe-9946-6d326f61bcb6	NOUN	\N	\N	130	{things}	2022-05-30
f8a3e40d-f42e-5ba2-995d-3abc33e63a41	NOUN	\N	\N	131	{things}	2022-05-30
1a0585e5-3bd0-5903-90cc-d3381778da68	NOUN	\N	\N	133	{things}	2022-05-30
1c5e45ac-cc4a-5d53-b566-d6d832828ba0	NOUN	\N	\N	134	{things}	2022-05-30
612eac67-3ec3-535e-94a3-249d60f51046	NOUN	\N	\N	135	{things}	2022-05-30
442d1c8e-a848-5fa4-8fc9-d398441b4b65	NOUN	\N	\N	136	{things}	2022-05-30
5f6cc5c3-6625-5caa-9277-f43493360fc2	NOUN	\N	\N	137	{things}	2022-05-30
6d055f49-622a-5d8a-a7d2-11de3279b9ce	NOUN	\N	\N	138	{things}	2022-05-30
3a52dad1-de68-523b-b6ad-3148496bcf8e	NOUN	\N	\N	139	{place}	2022-05-30
6c108fe0-b52d-56de-96f2-f8e0bf450367	NOUN	\N	\N	140	{place}	2022-05-30
14edc8de-8ed3-58f5-a083-13a34db6f490	NOUN	\N	\N	141	{place}	2022-05-30
dd52de46-6d78-5bc3-96ab-b25e9be9e291	NOUN	\N	\N	142	{place}	2022-05-30
89b553be-905d-57aa-946c-86dc9f366aa2	NOUN	\N	\N	143	{place}	2022-05-30
f7d4fade-03e1-53e3-94c0-24034e6a9e08	ADVERB	\N	\N	144	{money}	2022-05-30
fbae6594-96c7-53f0-a80b-bd325d5a22ff	NOUN	\N	\N	145	{money}	2022-05-30
dcb08e4f-eb7c-5536-bd57-ee51127a14be	ADJECTIVE	I_ADJECTIVE	\N	146	{money}	2022-05-30
1cee44df-fc33-5279-8d09-5051e714782f	EXPRESSION	\N	\N	147	{}	2022-05-30
845d24c6-6c7c-50f4-8dd7-450485839fa5	EXPRESSION	\N	\N	148	{}	2022-05-30
fc6be215-8cf3-55b8-8a6b-0dd345852963	EXPRESSION	\N	\N	149	{}	2022-05-30
3bb29baa-6e40-5c58-93cd-2000ea1120eb	EXPRESSION	\N	\N	150	{}	2022-05-30
8dc54b41-91cb-5289-bb0b-5f4a5854fea7	EXPRESSION	\N	\N	151	{}	2022-05-30
5891a34d-ba0a-5458-b42e-f82c60d27f9f	EXPRESSION	\N	\N	152	{}	2022-05-30
48b8587c-d363-5b54-94d9-c9e5dfff2412	NOUN	\N	\N	153	{number}	2022-05-30
c007b505-323f-5517-8353-ed093d1571de	NOUN	\N	\N	154	{number}	2022-05-30
8873834d-7358-505d-a93b-7782dbd00024	NOUN	\N	\N	155	{number}	2022-05-30
0f9e7c25-4cfa-5186-a893-0f59b7e34745	NOUN	\N	\N	156	{number}	2022-05-30
59d58d35-f0f2-5535-8404-8cb786e2c830	NOUN	\N	\N	157	{number}	2022-05-30
82339058-2123-58a8-93dc-b1c0b05cdae8	NOUN	\N	\N	158	{number}	2022-05-30
e469a7d8-cfad-5a85-ba5c-cb1d0c8ca1b0	NOUN	\N	\N	159	{number}	2022-05-30
49ab0509-4a86-552d-975b-fc49e74790d4	NOUN	\N	\N	160	{number}	2022-05-30
6d9b3812-644e-5890-94cc-09a2fd01a30a	NOUN	\N	\N	161	{number}	2022-05-30
aaf16bcd-1ecc-5f72-97a6-497825719aba	NOUN	\N	\N	162	{number}	2022-05-30
e05ed6a4-edcb-5052-9760-1e10c70c2236	NOUN	\N	\N	163	{number}	2022-05-30
\.


--
-- Data for Name: word_class; Type: TABLE DATA; Schema: vocabulary; Owner: postgres
--

COPY vocabulary.word_class (code, label) FROM stdin;
NOUN	Noun
PROPER_NOUN	Proper Noun
PRONOUN	Pronoun
ADJECTIVE	Adjective
VERB	Verb
ADVERB	Adverb
PREPOSITION	Preposition
INTERJECTION	Interjection
CONJUNCTION	Conjunction
PARTICLE	Particle
EXPRESSION	Expression
CONTRACTION	Contraction
\.


--
-- Name: lesson lesson_pkey; Type: CONSTRAINT; Schema: source; Owner: postgres
--

ALTER TABLE ONLY source.lesson
    ADD CONSTRAINT lesson_pkey PRIMARY KEY (code);


--
-- Name: source source_pkey; Type: CONSTRAINT; Schema: source; Owner: postgres
--

ALTER TABLE ONLY source.source
    ADD CONSTRAINT source_pkey PRIMARY KEY (code);


--
-- Name: conjugation_kind conjugation_kind_pkey; Type: CONSTRAINT; Schema: vocabulary; Owner: postgres
--

ALTER TABLE ONLY vocabulary.conjugation_kind
    ADD CONSTRAINT conjugation_kind_pkey PRIMARY KEY (code);


--
-- Name: definition definition_pkey; Type: CONSTRAINT; Schema: vocabulary; Owner: postgres
--

ALTER TABLE ONLY vocabulary.definition
    ADD CONSTRAINT definition_pkey PRIMARY KEY (id);


--
-- Name: linkages linkages_vocabulary_id_key; Type: CONSTRAINT; Schema: vocabulary; Owner: postgres
--

ALTER TABLE ONLY vocabulary.linkages
    ADD CONSTRAINT linkages_vocabulary_id_key UNIQUE (vocabulary_id);


--
-- Name: spelling_kind spelling_kind_pkey; Type: CONSTRAINT; Schema: vocabulary; Owner: postgres
--

ALTER TABLE ONLY vocabulary.spelling_kind
    ADD CONSTRAINT spelling_kind_pkey PRIMARY KEY (code);


--
-- Name: spelling spelling_pkey; Type: CONSTRAINT; Schema: vocabulary; Owner: postgres
--

ALTER TABLE ONLY vocabulary.spelling
    ADD CONSTRAINT spelling_pkey PRIMARY KEY (id);


--
-- Name: vocabulary vocabulary_pkey; Type: CONSTRAINT; Schema: vocabulary; Owner: postgres
--

ALTER TABLE ONLY vocabulary.vocabulary
    ADD CONSTRAINT vocabulary_pkey PRIMARY KEY (id);


--
-- Name: word_class word_class_pkey; Type: CONSTRAINT; Schema: vocabulary; Owner: postgres
--

ALTER TABLE ONLY vocabulary.word_class
    ADD CONSTRAINT word_class_pkey PRIMARY KEY (code);


--
-- Name: lesson_section_number_subsection_number_idx; Type: INDEX; Schema: source; Owner: postgres
--

CREATE INDEX lesson_section_number_subsection_number_idx ON source.lesson USING btree (section_number, subsection_number);


--
-- Name: lesson_source_code_idx; Type: INDEX; Schema: source; Owner: postgres
--

CREATE INDEX lesson_source_code_idx ON source.lesson USING btree (source_code);


--
-- Name: lesson_source_code_lesson_code_idx; Type: INDEX; Schema: source; Owner: postgres
--

CREATE UNIQUE INDEX lesson_source_code_lesson_code_idx ON source.lesson USING btree (source_code, lesson_code);


--
-- Name: definition_value_idx; Type: INDEX; Schema: vocabulary; Owner: postgres
--

CREATE INDEX definition_value_idx ON vocabulary.definition USING btree (value);


--
-- Name: definition_vocabulary_id_id_idx; Type: INDEX; Schema: vocabulary; Owner: postgres
--

CREATE UNIQUE INDEX definition_vocabulary_id_id_idx ON vocabulary.definition USING btree (vocabulary_id, id);


--
-- Name: definition_vocabulary_id_idx; Type: INDEX; Schema: vocabulary; Owner: postgres
--

CREATE INDEX definition_vocabulary_id_idx ON vocabulary.definition USING btree (vocabulary_id);


--
-- Name: reference_lesson_code_idx; Type: INDEX; Schema: vocabulary; Owner: postgres
--

CREATE INDEX reference_lesson_code_idx ON vocabulary.reference USING btree (lesson_code);


--
-- Name: reference_vocabulary_id_lesson_code_idx; Type: INDEX; Schema: vocabulary; Owner: postgres
--

CREATE UNIQUE INDEX reference_vocabulary_id_lesson_code_idx ON vocabulary.reference USING btree (vocabulary_id, lesson_code);


--
-- Name: spelling_spelling_kind_code_value_idx; Type: INDEX; Schema: vocabulary; Owner: postgres
--

CREATE INDEX spelling_spelling_kind_code_value_idx ON vocabulary.spelling USING btree (spelling_kind_code, value);


--
-- Name: spelling_value_idx; Type: INDEX; Schema: vocabulary; Owner: postgres
--

CREATE INDEX spelling_value_idx ON vocabulary.spelling USING btree (value);


--
-- Name: spelling_vocabulary_id_id_idx; Type: INDEX; Schema: vocabulary; Owner: postgres
--

CREATE UNIQUE INDEX spelling_vocabulary_id_id_idx ON vocabulary.spelling USING btree (vocabulary_id, id);


--
-- Name: spelling_vocabulary_id_idx; Type: INDEX; Schema: vocabulary; Owner: postgres
--

CREATE INDEX spelling_vocabulary_id_idx ON vocabulary.spelling USING btree (vocabulary_id);


--
-- Name: vocabulary_tags_idx; Type: INDEX; Schema: vocabulary; Owner: postgres
--

CREATE INDEX vocabulary_tags_idx ON vocabulary.vocabulary USING gin (tags);


--
-- Name: lesson lesson_source_code_fkey; Type: FK CONSTRAINT; Schema: source; Owner: postgres
--

ALTER TABLE ONLY source.lesson
    ADD CONSTRAINT lesson_source_code_fkey FOREIGN KEY (source_code) REFERENCES source.source(code);


--
-- Name: conjugation_kind conjugation_kind_word_class_code_fkey; Type: FK CONSTRAINT; Schema: vocabulary; Owner: postgres
--

ALTER TABLE ONLY vocabulary.conjugation_kind
    ADD CONSTRAINT conjugation_kind_word_class_code_fkey FOREIGN KEY (word_class_code) REFERENCES vocabulary.word_class(code);


--
-- Name: definition definition_vocabulary_id_fkey; Type: FK CONSTRAINT; Schema: vocabulary; Owner: postgres
--

ALTER TABLE ONLY vocabulary.definition
    ADD CONSTRAINT definition_vocabulary_id_fkey FOREIGN KEY (vocabulary_id) REFERENCES vocabulary.vocabulary(id);


--
-- Name: linkages linkages_vocabulary_id_alt_phonetic_spelling_id_fkey; Type: FK CONSTRAINT; Schema: vocabulary; Owner: postgres
--

ALTER TABLE ONLY vocabulary.linkages
    ADD CONSTRAINT linkages_vocabulary_id_alt_phonetic_spelling_id_fkey FOREIGN KEY (vocabulary_id, alt_phonetic_spelling_id) REFERENCES vocabulary.spelling(vocabulary_id, id);


--
-- Name: linkages linkages_vocabulary_id_fkey; Type: FK CONSTRAINT; Schema: vocabulary; Owner: postgres
--

ALTER TABLE ONLY vocabulary.linkages
    ADD CONSTRAINT linkages_vocabulary_id_fkey FOREIGN KEY (vocabulary_id) REFERENCES vocabulary.vocabulary(id);


--
-- Name: linkages linkages_vocabulary_id_kanji_spelling_id_fkey; Type: FK CONSTRAINT; Schema: vocabulary; Owner: postgres
--

ALTER TABLE ONLY vocabulary.linkages
    ADD CONSTRAINT linkages_vocabulary_id_kanji_spelling_id_fkey FOREIGN KEY (vocabulary_id, kanji_spelling_id) REFERENCES vocabulary.spelling(vocabulary_id, id);


--
-- Name: linkages linkages_vocabulary_id_phonetic_spelling_id_fkey; Type: FK CONSTRAINT; Schema: vocabulary; Owner: postgres
--

ALTER TABLE ONLY vocabulary.linkages
    ADD CONSTRAINT linkages_vocabulary_id_phonetic_spelling_id_fkey FOREIGN KEY (vocabulary_id, phonetic_spelling_id) REFERENCES vocabulary.spelling(vocabulary_id, id);


--
-- Name: linkages linkages_vocabulary_id_preferred_definition_id_fkey; Type: FK CONSTRAINT; Schema: vocabulary; Owner: postgres
--

ALTER TABLE ONLY vocabulary.linkages
    ADD CONSTRAINT linkages_vocabulary_id_preferred_definition_id_fkey FOREIGN KEY (vocabulary_id, preferred_definition_id) REFERENCES vocabulary.definition(vocabulary_id, id);


--
-- Name: linkages linkages_vocabulary_id_preferred_spelling_id_fkey; Type: FK CONSTRAINT; Schema: vocabulary; Owner: postgres
--

ALTER TABLE ONLY vocabulary.linkages
    ADD CONSTRAINT linkages_vocabulary_id_preferred_spelling_id_fkey FOREIGN KEY (vocabulary_id, preferred_spelling_id) REFERENCES vocabulary.spelling(vocabulary_id, id);


--
-- Name: reference reference_lesson_code_fkey; Type: FK CONSTRAINT; Schema: vocabulary; Owner: postgres
--

ALTER TABLE ONLY vocabulary.reference
    ADD CONSTRAINT reference_lesson_code_fkey FOREIGN KEY (lesson_code) REFERENCES source.lesson(code);


--
-- Name: reference reference_vocabulary_id_fkey; Type: FK CONSTRAINT; Schema: vocabulary; Owner: postgres
--

ALTER TABLE ONLY vocabulary.reference
    ADD CONSTRAINT reference_vocabulary_id_fkey FOREIGN KEY (vocabulary_id) REFERENCES vocabulary.vocabulary(id);


--
-- Name: spelling spelling_spelling_kind_code_fkey; Type: FK CONSTRAINT; Schema: vocabulary; Owner: postgres
--

ALTER TABLE ONLY vocabulary.spelling
    ADD CONSTRAINT spelling_spelling_kind_code_fkey FOREIGN KEY (spelling_kind_code) REFERENCES vocabulary.spelling_kind(code);


--
-- Name: spelling spelling_vocabulary_id_fkey; Type: FK CONSTRAINT; Schema: vocabulary; Owner: postgres
--

ALTER TABLE ONLY vocabulary.spelling
    ADD CONSTRAINT spelling_vocabulary_id_fkey FOREIGN KEY (vocabulary_id) REFERENCES vocabulary.vocabulary(id);


--
-- Name: vocabulary vocabulary_conjugation_kind_code_fkey; Type: FK CONSTRAINT; Schema: vocabulary; Owner: postgres
--

ALTER TABLE ONLY vocabulary.vocabulary
    ADD CONSTRAINT vocabulary_conjugation_kind_code_fkey FOREIGN KEY (conjugation_kind_code) REFERENCES vocabulary.conjugation_kind(code);


--
-- Name: vocabulary vocabulary_word_class_code_fkey; Type: FK CONSTRAINT; Schema: vocabulary; Owner: postgres
--

ALTER TABLE ONLY vocabulary.vocabulary
    ADD CONSTRAINT vocabulary_word_class_code_fkey FOREIGN KEY (word_class_code) REFERENCES vocabulary.word_class(code);


--
-- PostgreSQL database dump complete
--

