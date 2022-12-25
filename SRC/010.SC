;**
;**	Script 10 -- Menu suite; outputs to script.997 (not 010 as supposed)
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	January 27, 1990
;**


;**	Break lines in "AddMenu" before a divider, for aesthetics
;**	= sets a menu item's starting value
;**	! makes the item non-selectable
;**	` denotes the following character is the key to press
;**	: separates menu items within a menu stack


(script# MENU)									;**	output to script.997


(include "menu.sh")


(procedure
	TglSound
)

(public
	TglSound			1
)

(local
	fermata
)


(define	vPause						050)
(define	vAboutLL3					051)
(define	vQuit							055)
(define	vRestart						057)


(class TheMenuBar kindof MenuBar			;**	MENUBAR
	(method (init)
		(AddMenu { \01 }
			{About LSL3`^a:Help`#1}
		)

		(AddMenu { File }
			{Save Game`#5:Restore Game`#7:Auto Save`#4:--!
			:Restart Game`#9:Quit`^q}
		)

		(AddMenu { Action }
			{Pause Game`^p:Inventory`^I:Retype`#3:--!
			:Colors`^c:--!
			:Boss Key`^b
			:Expletive`^x}
		)
			;**	:Filth Level`^f

		(AddMenu { Speed }
			{Change...`^s:--!:Faster`+:Normal`=:Slower`-}
			;**	:--!:Text...`^t}
		)

		(AddMenu { Sound }
			{Volume...`^v:Turn Off`#2=1}
		)

		(SetMenu soundI #p_text
			(if (DoSound SoundOn)
				{Turn Off}
			else
				{Turn On}
			)
		)

		(if ( < (Graph GDetect) 9)			;**	Monochrome or CGA
			(SetMenu colorsI #state FALSE)
		else		
			(SetMenu colorsI #p_said '/color')
		)

		(SetMenu saveI			#p_said 'save[/game]')
		(SetMenu restoreI		#p_said 'restore[/game]')
		(SetMenu restartI		#p_said 'restart[/game]')
		(SetMenu quitI			#p_said 'quit[/game]')
		(SetMenu	pauseI		#p_said 'pause[/game]')
		(SetMenu invI			#p_said 'inventory')
	)


	(method (handleEvent event &tmp msg i t b [string 200] [string2 20])
		(switch (= msg (super handleEvent event))


			;**************		SIERRA MENU		**************

			(aboutI
				(Print
					"\"Leisure Suit Larry 3:\n
					Passionate Patti in Pursuit\n
					of the Pulsating Pectorals!\"\n
					\n
					Designed by Al Lowe\n
					\n
					Programmed by Al Lowe\n
					and Carlos Escobar\n
					\n
					Graphics by William Skirvin\n
					and Roger Hardy\n
					"
					#font			smallFont
					#mode			teJustCenter
					#title		{An Al Lowe Production}
					#icon			vAboutLL3 0 0
				)
				(Print
					(Format @string "\n
						Sierra's Creative Interpreter\n
						by Jeff Stephenson, Bob Heitman,\n
						Pablo Ghenis, and Stuart Goldstein\n
						\n
						Music Composed and Performed by Mike Dana\n
						Sound Effects by Rob Atesalp\n
						Atari ST Translation by Corey Cole\n
						Amiga Translation by Robert Lindsley\n
						\n
						Quality Assurance led by Robin Bradley\n
						\n
						Version %s
						January 27, 1990
						\n
						\n
						"
						version
					)
					#font			smallFont
					#mode			teJustCenter
					#title		{The Cast of Thousands}
					#at			-1 30
					#width		234
				)
;**							Macintosh Translation by Bye\n

				(Format @filthLevelBuffer "%s" 
					(switch filthLevel
						(RAUNCHY			{Totally Raunchiest})
						(DIRTY			{Really Filthy})
						(RISQUE			{Pretty Dirty})
						(CLEAN			{Rather Risque})
						(else				{Mother Goose})
					)
				)
				(Print
					(cond
						(gameHours
							(Format @string
								"How could you play at the \"%s Level\" 
								for %d hour%s,
								%d minute%s and %d second%s and
								only score %d point%s?!"
								@filthLevelBuffer
								gameHours
								(if (== gameHours 1) {} else {s})
								gameMinutes
								(if (== gameMinutes 1) {} else {s})
								gameSeconds
								(if (== gameSeconds 1) {} else {s})
								score
								(if (== score 1) {} else {s})
							)
						)
						(score
							(Format @string
								"You've been playing this game at the \"%s Level\"
								for %d minute%s and %d second%s and
								you've only scored %d point%s?!"
								@filthLevelBuffer
								gameMinutes
								(if (== gameMinutes 1) {} else {s})
								gameSeconds
								(if (== gameSeconds 1) {} else {s})
								score
								(if (== score 1) {} else {s})
							)
						)
						(else
							(Format @string
								"You've been playing this game at the \"%s Level\" 
								for %d minute%s and %d second%s and
								you haven't scored a single point?"
								@filthLevelBuffer
								gameMinutes
								(if (== gameMinutes 1) {} else {s})
								gameSeconds
								(if (== gameSeconds 1) {} else {s})
							)
						)
					)
					#font			smallFont
					#mode			teJustCenter
					#title		{Get a Life!}
				)
			)

			(helpI
				(Print
					"_____DURING THE GAME:\n
					Click at the top of the screen or
					press ESC to use the menus.
					Additional shortcuts are shown there.\n\n

					_____IN TYPING WINDOWS:\n
					Arrows, Home and End move the cursor,
					or click anywhere with the mouse.
					Ctrl-C clears the line.\n\n

					_____IN DIALOG WINDOWS:\n
					Enter selects the outlined item, or
					click on items with the mouse.
					Tab and Shift-Tab move between choices.
					ESC always cancels.
					"
					#font		smallFont
					WIDE
				)

				(Print
					"If you really need help,
					pick up a copy of the \"Passionate Patti\"
					hint book at your software dealer.
					"
					#font		smallFont
				)
			)


			;**************		FILE MENU		**************

			(saveI
				(if (TstFlg cantSave)
					(Print "Sorry, but you can not save your game at this time.
						Please try again later."
						#title	{Not now, I have a headache!}
					)
				else
					(theGame save)
					(= asMinutes 0)
					(= asSeconds 0)
				)
			)

			(restoreI
				(theGame restore)
			)

			(autosaveI
				(if ( > 0 (= autoSaveMinutes (GetNumber {Minutes Between Reminders:} autoSaveMinutes)))
					(= autoSaveMinutes 0)
				)
			)

			(restartI
				(if (Print "Are you serious?
						You want to start all the way back at the beginning again?"
						#title		{Restart}
						#icon			vRestart 0 egoIsPatti
						#font			bigFont
						#button		{Restart} 	1
						#button		{_Oops_} 	0
					)
					(theGame restart)
				)
			)

			(quitI
				(= quit (Print (Format @string
						"Quit? Now? When you're doing so well?
						Why, you're almost up to %d points!"
						(Random (+ score 5) (+ score 50))
						)
						#title		{Quit}
						#icon			vQuit	0 egoIsPatti
						#font			bigFont
						#button		{_Quit_} 	1
						#button		{_Oops_} 	0
					)
				)
			)


			;**************		ACTION MENU		**************

			(pauseI
				(= fermata (Sound pause TRUE))
				(Print
					"So what am I supposed to do,
					just stand here while you do something else?
					Oh, all right.
					I'll just hold my breath until you return..."
					#title		{This game is paused.}
					#icon			vPause 0 egoIsPatti
					#font			bigFont
					#button		{Stop your whining!} 1
				)
				(Sound pause fermata)
			)

			(invI
				(if (HaveMem 1024)
					(inventory showSelf	ego)
				)
			)

			(repeatI
				(event
					claimed		FALSE,
					type			keyDown,
					message		(User echo),
				)
			)

			(colorsI
				(= t 16)
				(while (and	(u> t 15)
								(!= t ERROR))
					(= t (GetNumber {New Text Color: (0-15)}))
				)
				(if (!= t ERROR)
					(= b 16)
					(while (and (!= b ERROR)
									(or	(u> b 15)
											(== b t)))
						(= b (GetNumber {New Background Color: (0-15)}))
					)
					(if (!= b ERROR)
						(= textColor t)
						(= backgroundColor b)
					)
				)
				(systemWindow
					color 		textColor,
					back			backgroundColor,
				)
			)

			(bossI
				(curRoom newRoom BOSS_KEY)	;**	script #90
			)

;**				(filthI
;**					(if (HaveMem 2048)
;**						(= filthLevel ((Gauge new)
;**								description	{Use the mouse or the left and right arrow 
;**												keys to suit your taste.},
;**								text			{Filth-O-Meter},
;**								higher		{Dirtier},
;**								lower			{Cleaner},
;**								doit			filthLevel
;**							)
;**						)
;**						(cond
;**							((== filthLevel 0)
;**								(Print "Perhaps you should buy \"Mother Goose!\"")
;**							)
;**							((<= filthLevel CLEAN)
;**								(Print "Thank you, I hate it when you talk dirty!")
;**							)
;**							(( > filthLevel 14)
;**								(Print "Your mother would be so proud!")
;**							)
;**							(else
;**								(Print "Naughty, naughty!")
;**							)
;**						)
;**						(DisposeScript GAUGE)
;**					)
;**				)

			(expletiveI
				(GetInput (Format @string expletive) 38
					{Enter your favorite expletive:})
				(if ( > (StrLen @string) 4)
					(Format expletive @string)
				)
			)


			;**************		SPEED MENU		**************

			(speedI
				(if (HaveMem 2048)
					(= i
						((Gauge new)
							description	{Use the mouse or the left and right arrow 
											keys to select the speed of moving characters.},
							text			{Animation Speed},
							normal		10,		;**	that's really (- 16 10)
							higher		{Faster},
							lower			{Slower},
							doit			(- 16 speed)
						)
					)
					(theGame setSpeed (- 16 i))
					(DisposeScript GAUGE)
				)
			)

			(fasterI
				(if (> speed (^ 1 (= i debugging))) ;This lets Al (only) haul ass!
					(theGame setSpeed (-- speed))
				)
			)

			(normalI
				(theGame setSpeed 6)
			)

			(slowerI
				(theGame setSpeed (++ speed))
			)

;**				(textSpeedI
;**					(if (HaveMem 2048)
;**						(= i
;**							((Gauge new)
;**								description	{Use the mouse or the left and right arrow 
;**												keys to select the speed of text messages.},
;**								text			{Text Speed},
;**								normal		5,
;**								higher		{Faster},
;**								lower			{Slower},
;**								doit			(/ (- stringDelay 10) 2),
;**							)
;**						)
;**						(= stringDelay (+ 10 (* 2 i)))
;**						(DisposeScript GAUGE)
;**					)
;**				)

			;**************		SOUND MENU		**************

			(volumeI
				(if (HaveMem 2048)
					(= i
						((Gauge new)
							description	{Use the mouse or the left and right arrow
											keys to adjust the volume.},
							text			{Sound Volume},
							normal		15,
							higher		{Louder},
							lower			{Softer},
							doit			(DoSound ChangeVolume i),
						)
					)
					(DoSound ChangeVolume i)
					(DisposeScript GAUGE)
				)
			)

			(soundI
				(TglSound)
;**					(= i (DoSound SoundOn))
;**					(DoSound SoundOn (not i))	;**	Backwards (and proud of it)
;**					(if i
;**						(SetMenu soundI #p_text {Turn On})
;**						(Print "Sound is off.")
;**					else
;**						(SetMenu soundI #p_text {Turn Off})
;**						(Print "Sound is on.")
;**					)
			)
		)
	)
)

(procedure (TglSound &tmp i)
	(= i (DoSound SoundOn))
	(DoSound SoundOn (not i))				;**	Backwards (and proud of it)
	(if i
		(SetMenu soundI #p_text {Turn On})
	else
		(SetMenu soundI #p_text {Turn Off})
	)
)

