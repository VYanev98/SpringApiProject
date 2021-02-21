CREATE TABLE [holidays](
	[id] [int] NOT NULL,
	[country_name] [varchar](255) NULL,
	[date_date_time_day] [varchar](255) NULL,
	[date_date_time_month] [varchar](255) NULL,
	[date_date_time_year] [varchar](255) NULL,
	[date_iso] [varchar](255) NULL,
	[name_text] [varchar](255) NULL,
	[oneliner_text] [varchar](255) NULL,
	[types] [varchar](255) NULL,
	[url] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO


