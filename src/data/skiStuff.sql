--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.3
-- Dumped by pg_dump version 9.5.3

-- Started on 2016-06-29 14:42:00

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 1 (class 3079 OID 12355)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2142 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 188 (class 1259 OID 16460)
-- Name: customer_order; Type: TABLE; Schema: public; Owner: rupert
--

CREATE TABLE customer_order (
    id integer NOT NULL,
    pid integer,
    email character varying(50) NOT NULL,
    quantity integer NOT NULL
);


ALTER TABLE customer_order OWNER TO rupert;

--
-- TOC entry 187 (class 1259 OID 16458)
-- Name: customer_order_id_seq; Type: SEQUENCE; Schema: public; Owner: rupert
--

CREATE SEQUENCE customer_order_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE customer_order_id_seq OWNER TO rupert;

--
-- TOC entry 2143 (class 0 OID 0)
-- Dependencies: 187
-- Name: customer_order_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: rupert
--

ALTER SEQUENCE customer_order_id_seq OWNED BY customer_order.id;


--
-- TOC entry 184 (class 1259 OID 16409)
-- Name: employee; Type: TABLE; Schema: public; Owner: rupert
--

CREATE TABLE employee (
    id integer NOT NULL,
    name character varying(50) NOT NULL,
    department character varying(50) NOT NULL
);


ALTER TABLE employee OWNER TO rupert;

--
-- TOC entry 183 (class 1259 OID 16407)
-- Name: employee_id_seq; Type: SEQUENCE; Schema: public; Owner: rupert
--

CREATE SEQUENCE employee_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE employee_id_seq OWNER TO rupert;

--
-- TOC entry 2144 (class 0 OID 0)
-- Dependencies: 183
-- Name: employee_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: rupert
--

ALTER SEQUENCE employee_id_seq OWNED BY employee.id;


--
-- TOC entry 186 (class 1259 OID 16417)
-- Name: salary; Type: TABLE; Schema: public; Owner: rupert
--

CREATE TABLE salary (
    id integer NOT NULL,
    salary numeric(10,2),
    emp_id integer
);


ALTER TABLE salary OWNER TO rupert;

--
-- TOC entry 185 (class 1259 OID 16415)
-- Name: salary_id_seq; Type: SEQUENCE; Schema: public; Owner: rupert
--

CREATE SEQUENCE salary_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE salary_id_seq OWNER TO rupert;

--
-- TOC entry 2145 (class 0 OID 0)
-- Dependencies: 185
-- Name: salary_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: rupert
--

ALTER SEQUENCE salary_id_seq OWNED BY salary.id;


--
-- TOC entry 182 (class 1259 OID 16397)
-- Name: skisetc; Type: TABLE; Schema: public; Owner: rupert
--

CREATE TABLE skisetc (
    id integer NOT NULL,
    product character varying(50) NOT NULL,
    category character varying(50) NOT NULL,
    price numeric(10,2)
);


ALTER TABLE skisetc OWNER TO rupert;

--
-- TOC entry 181 (class 1259 OID 16395)
-- Name: skisetc_id_seq; Type: SEQUENCE; Schema: public; Owner: rupert
--

CREATE SEQUENCE skisetc_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE skisetc_id_seq OWNER TO rupert;

--
-- TOC entry 2146 (class 0 OID 0)
-- Dependencies: 181
-- Name: skisetc_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: rupert
--

ALTER SEQUENCE skisetc_id_seq OWNED BY skisetc.id;


--
-- TOC entry 2002 (class 2604 OID 16463)
-- Name: id; Type: DEFAULT; Schema: public; Owner: rupert
--

ALTER TABLE ONLY customer_order ALTER COLUMN id SET DEFAULT nextval('customer_order_id_seq'::regclass);


--
-- TOC entry 2000 (class 2604 OID 16412)
-- Name: id; Type: DEFAULT; Schema: public; Owner: rupert
--

ALTER TABLE ONLY employee ALTER COLUMN id SET DEFAULT nextval('employee_id_seq'::regclass);


--
-- TOC entry 2001 (class 2604 OID 16420)
-- Name: id; Type: DEFAULT; Schema: public; Owner: rupert
--

ALTER TABLE ONLY salary ALTER COLUMN id SET DEFAULT nextval('salary_id_seq'::regclass);


--
-- TOC entry 1999 (class 2604 OID 16400)
-- Name: id; Type: DEFAULT; Schema: public; Owner: rupert
--

ALTER TABLE ONLY skisetc ALTER COLUMN id SET DEFAULT nextval('skisetc_id_seq'::regclass);


--
-- TOC entry 2134 (class 0 OID 16460)
-- Dependencies: 188
-- Data for Name: customer_order; Type: TABLE DATA; Schema: public; Owner: rupert
--

COPY customer_order (id, pid, email, quantity) FROM stdin;
1	3	customer@no-email.com	4
2	9	customer@no-email.com	10
\.


--
-- TOC entry 2147 (class 0 OID 0)
-- Dependencies: 187
-- Name: customer_order_id_seq; Type: SEQUENCE SET; Schema: public; Owner: rupert
--

SELECT pg_catalog.setval('customer_order_id_seq', 2, true);


--
-- TOC entry 2130 (class 0 OID 16409)
-- Dependencies: 184
-- Data for Name: employee; Type: TABLE DATA; Schema: public; Owner: rupert
--

COPY employee (id, name, department) FROM stdin;
1	Moe Stooge	Accounting
3	Larry Stooger	Recruitmenters
2	Curly Stooges	Financer
\.


--
-- TOC entry 2148 (class 0 OID 0)
-- Dependencies: 183
-- Name: employee_id_seq; Type: SEQUENCE SET; Schema: public; Owner: rupert
--

SELECT pg_catalog.setval('employee_id_seq', 3, true);


--
-- TOC entry 2132 (class 0 OID 16417)
-- Dependencies: 186
-- Data for Name: salary; Type: TABLE DATA; Schema: public; Owner: rupert
--

COPY salary (id, salary, emp_id) FROM stdin;
1	123456.78	1
3	324154.50	3
2	132435.50	2
\.


--
-- TOC entry 2149 (class 0 OID 0)
-- Dependencies: 185
-- Name: salary_id_seq; Type: SEQUENCE SET; Schema: public; Owner: rupert
--

SELECT pg_catalog.setval('salary_id_seq', 3, true);


--
-- TOC entry 2128 (class 0 OID 16397)
-- Dependencies: 182
-- Data for Name: skisetc; Type: TABLE DATA; Schema: public; Owner: rupert
--

COPY skisetc (id, product, category, price) FROM stdin;
3	Scarpa Gia	Telemark boot	479.89
4	Scarpa TRace	Telemark boot	579.59
5	Salamon XPro 120	Alpine boot	360.99
6	Lange RX 100	Alpine boot	455.99
8	Scarpa T4 Backcountry	Cross country boot	224.59
9	Fischer RC3 Skate	Cross country boot	145.55
10	Dynafit 7 Summits	Telemark skis	546.77
11	DPS SKIS Nina	Telemark skis	677.88
12	LaSportiva 99E	Alpine touring skis	689.49
13	Black Diamond Verdict	Alpine touring skis	499.99
14	K2 Iconic	Alpine skis	699.49
15	Volkl RTM 81	Alpine skis	726.99
16	Atomic Theory	Alpine skis	598.95
17	Fischer CRS Classic Vasa	Cross country skis	189.95
18	Salomon Snowscape Classic	Cross country skis	228.95
19	Fischer CRS Skate	Cross country skis	194.59
20	Atomic Redstar Skate	Cross country skis	321.99
7	Rossignol X6 Combi	Cross country boot	145.95
2	Black Diamond Factor 110	Telemark Boot	244.65
1	Scott Synergy	Telemark Boot	450.95
\.


--
-- TOC entry 2150 (class 0 OID 0)
-- Dependencies: 181
-- Name: skisetc_id_seq; Type: SEQUENCE SET; Schema: public; Owner: rupert
--

SELECT pg_catalog.setval('skisetc_id_seq', 57, true);


--
-- TOC entry 2010 (class 2606 OID 16465)
-- Name: customer_order_pkey; Type: CONSTRAINT; Schema: public; Owner: rupert
--

ALTER TABLE ONLY customer_order
    ADD CONSTRAINT customer_order_pkey PRIMARY KEY (id);


--
-- TOC entry 2006 (class 2606 OID 16414)
-- Name: employee_pkey; Type: CONSTRAINT; Schema: public; Owner: rupert
--

ALTER TABLE ONLY employee
    ADD CONSTRAINT employee_pkey PRIMARY KEY (id);


--
-- TOC entry 2008 (class 2606 OID 16422)
-- Name: salary_pkey; Type: CONSTRAINT; Schema: public; Owner: rupert
--

ALTER TABLE ONLY salary
    ADD CONSTRAINT salary_pkey PRIMARY KEY (id);


--
-- TOC entry 2004 (class 2606 OID 16402)
-- Name: skisetc_pkey; Type: CONSTRAINT; Schema: public; Owner: rupert
--

ALTER TABLE ONLY skisetc
    ADD CONSTRAINT skisetc_pkey PRIMARY KEY (id);


--
-- TOC entry 2012 (class 2606 OID 16466)
-- Name: customer_order_pid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: rupert
--

ALTER TABLE ONLY customer_order
    ADD CONSTRAINT customer_order_pid_fkey FOREIGN KEY (pid) REFERENCES skisetc(id);


--
-- TOC entry 2011 (class 2606 OID 16423)
-- Name: salary_emp_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: rupert
--

ALTER TABLE ONLY salary
    ADD CONSTRAINT salary_emp_id_fkey FOREIGN KEY (emp_id) REFERENCES employee(id);


--
-- TOC entry 2141 (class 0 OID 0)
-- Dependencies: 6
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2016-06-29 14:42:00

--
-- PostgreSQL database dump complete
--

