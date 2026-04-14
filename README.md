# 📊 TalendETLProject – Data Intelligence Platform

## Overview

This project was developed as part of the **PABI – 4th Year Engineering Program** at **Esprit School of Engineering** (Academic Year 2025–2026).

It is a end-to-end **Data Intelligence platform** built following the **GIMSI methodology**, covering data ingestion, transformation, warehousing, visualization, machine learning, and web deployment.

The solution enables decision-makers to gain actionable insights through automated ETL pipelines, interactive Power BI dashboards, and ML-powered predictions.

---

## Features

- 🔄 **ETL Pipelines** – Automated data extraction, transformation, and loading using Talend Open Studio
- 🏗️ **Data Warehouse** – Structured DW schema designed for analytical queries
- 📊 **Power BI Dashboards** – Interactive reports with KPIs and business insights
- 🤖 **Machine Learning Models** – Predictive models trained, tuned and evaluated (Accuracy, F1-Score, RMSE)
- ⚙️ **Workflow Automation** – Orchestration of data jobs and automation using n8n
- 🌐 **Web Application** – A wrapper web app exposing the solution to end users
- 🚀 **MLOps Pipeline** – Model deployment, monitoring and drift tracking

---

## Tech Stack

### Data Integration & ETL
- Talend Open Studio

### Data Warehouse
- SQL / Relational Database (Staging Area → Data Warehouse)

### Visualization & BI
- Power BI Desktop (.pbix)

### Machine Learning
- Python (Jupyter Notebooks)
- Scikit-learn / relevant ML libraries

### Automation & Orchestration
- n8n (Workflow Automation)

### Web Application
- Angular (Frontend)
- Django (Backend)

---

## Architecture

```
Raw Data Sources
      ↓
 Staging Area (V1)
      ↓
  ETL (Talend)
      ↓
 Data Warehouse
      ↓
 ┌────────────────────────┐
 │  Power BI Dashboards   │
 │  ML Models (Python)    │
 │  Web App Wrapper       │
 └────────────────────────┘
      ↓
  MLOps Monitoring
```

The project follows the **GIMSI methodology** across 4 phases:

1. **Identification** – Topic assignment, team setup, initial EDA, specifications draft
2. **Design** – DW schema, KPI definition, dashboard mockups, web scraping, architecture validation
3. **Implementation** – ETL development, DW population, Power BI V1, ML training, n8n automation, web app deployment
4. **Permanent Improvement** – System audit, MLOps monitoring, end-to-end testing, final defense

---

## Project Timeline

| Phase | Weeks | Key Deliverables |
|-------|-------|-----------------|
| Identification | S1 – S3 | Team setup, EDA, Staging Area V1, Specs Draft 1 |
| Design | S4 – S6 | DW Schema, KPI List, Dashboard Mockups, Validated Architecture |
| Implementation | S7 – S12 | ETL, Power BI V1, ML Models, n8n Workflows, Web App |
| Permanent Improvement | S13 – S15 | MLOps, Final Report, Video Demo, Final Defense |

---

## Contributors

| Nom | GitHub |
|-----|--------|
| Chaima Cherif |[@Chaima-Cherif391](https://github.com/Chaima-Cherif391) |
| Mariem Chouchene|[@mariemchouchene](https://github.com/Mariem0210) |
| Molka Gharbi |[@GharbiMolka](https://github.com/GharbiMolka) |

---

## Academic Context

Developed at **Esprit School of Engineering – Tunisia**
**PABI – 4ERP-BI7 | 2025–2026**
Methodology: **GIMSI**

---

## Getting Started

### Prerequisites
- Talend Open Studio installed
- Power BI Desktop installed
- Python 3.x + pip
- Node.js (for web app)
- n8n (local or cloud)

### Installation

```bash
# Clone the repository
git clone https://github.com/Chaima-Cherif391/Esprit-PABI-4ERP-BI7-2026-TalendETLProject.git

# Navigate to the project
cd Esprit-PABI-4ERP-BI7-2026-TalendETLProject

# Install Python dependencies
pip install -r requirements.txt
```

### Running the ETL
```bash
# Open Talend Open Studio and import the jobs from /talend folder
# Run jobs in order: Staging → Transform → DW Load
```

### Viewing Dashboards
```bash
# Open /powerbi/dashboard.pbix in Power BI Desktop
# Or access the published report via the link below:

```

---

## Acknowledgments

- **Esprit School of Engineering** – Tunisia, for providing the academic framework
- Coaches and supervisors for their guidance throughout the GIMSI methodology phases
- Open-source communities behind Talend, Python, Power BI, and n8n
