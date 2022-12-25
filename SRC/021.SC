;**
;**	Logics for script 21 -- just a showState procedure
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	September 25, 1989
;**


(script# 	SHOW_STATE)

(procedure
	ShowState
)

(public
	ShowState		1
)


(procedure (ShowState whatScript newState where color &tmp [str 33])
	(if (and debugging
				(not (TstFlg inQA)))
		(if (< argc 2)		(= where 1))
		(if (< argc 3)		(= color white))
		(Display (Format @str " %s was state %d; is now state %d. "
			(whatScript name) (whatScript state) newState)
				#p_at			1 (- (* 8 where) 7)
				#p_font		SANS_SERIF_8
				#p_color		color
				#p_back		black
		)
	)
)

