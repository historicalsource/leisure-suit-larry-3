;**
;**	Logics for script 40 -- Dying script
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	October 10, 1989
;**


(script#				40)

(public
	DyingScript		0
)


;**		When using this script, make its REGISTER property a pointer to 
;**	the message to be printed, its CALLER property the view number of 
;**	the icon view to be shown, and its NEXT property a pointer to the
;**	string to use for the title bar. See 041.sc for proper usage.


(define	sDead							002)


(instance DyingScript of Script
	(method (changeState newState &tmp [string 100] [string2 33])
		(switch (= state newState)

			(0
				(HandsOff)
				(music			fade)
				(curRoom setScript FALSE)
				(= egoState GAMEOVER)

				(Load SOUND sDead)
				(delay 3)
			)

			(1
				(Load VIEW caller)
				(soundFX			stop)
				(music
					number		sDead,
					loop			1,
					priority		255,
					play,
				)

				;**	Police up, just in case!
;**					(if (not next)
;**						(= next (Format @string2 "Oh, no! Not again?!"))
;**					)
;**					(if (not register)
;**						(= register (Format @string "You sure screwed up THAT time!"))
;**					)
;**					(if (not caller)
;**						(= caller (ego view))
;**					)
				(deadIcon 
					view			caller,
				)


				;**	Everything's set up by now.
				(if (Print register
						#title	next
						#font 	bigFont
						#icon 	deadIcon
						#button	{Keep On Muddling} 0
						#button	{Order A Hintbook} 1
					)
					(Print "See your local software dealer or
						dial (800) 326-6654 from 8-5 Pacific Time.
						Have your credit card handy.")
				)
				(repeat
					(switch (Print "Remember:\n
									save early, save often!"
									#title	{Al says:}
									#font 	bigFont
									#button	{Restore}	1
									#button	{Restart}	2
									#button	{__Quit__}	3)
						(1	(theGame restore))
						(2	(theGame restart))
						(3	(= quit TRUE)
							(break)
						)
					)
				)
			)
		)
	)

)
(instance deadIcon of DCIcon
	(method (init)
		(super init)

		(if (== curRoomNum 540)
			((= cycler 	(EndLoop new)) init self)
		)
	)
)
